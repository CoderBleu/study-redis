package cn.coderblue.redislockv.controller;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * redis分布式锁
 *
 * @author coderblue
 */
@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedissonClient redissonClient;

    private final String LOCK_KEY = "test-redis-lock";
    private final String REDIS_LOCK = "redisLock";

    @GetMapping("/set/{value}")
    public String setValue(@PathVariable String value) {
        // 没有不存在此key就进行操作返回true，否则不操作，返回false。jedis.setnx(key, value)
        // 设置10s过期时间
        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(this.REDIS_LOCK, value, 10, TimeUnit.MINUTES);
        if (!result) {
            return "error";
        }
        return "success";
    }

    @GetMapping
    public String test() {
        RLock redissonLock = redissonClient.getLock(this.LOCK_KEY);

        try {
            // 30 S
            redissonLock.lock(30, TimeUnit.SECONDS);
            Integer count = Integer.parseInt(stringRedisTemplate.opsForValue().get(this.REDIS_LOCK));
            if (count > 0) {
                int realCount = count - 1;
                stringRedisTemplate.opsForValue().set(this.REDIS_LOCK, realCount + "");
                System.out.println("扣减成功，剩余库存：" + realCount);
            } else {
                System.out.println("扣减失败，库存不够");
            }
        } finally {
            redissonLock.unlock();
        }
        return "success";
    }

    /**
     * 不推荐,单部署下还可以
     * @return
     */
    @GetMapping("/test1/{value}")
    public String test1(@PathVariable String value) {

        Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(this.REDIS_LOCK, value, 10, TimeUnit.MINUTES);
        if (!result) {
            return "error";
        }

        String clientId = UUID.randomUUID().toString().replace("-", "");
        try {
            // 30 S
            Integer count = Integer.parseInt(stringRedisTemplate.opsForValue().get(this.REDIS_LOCK));
            if (count > 0) {
                int realCount = count - 1;
                stringRedisTemplate.opsForValue().set(this.REDIS_LOCK, realCount + "");
                System.out.println("扣减成功，剩余库存：" + realCount);
            } else {
                System.out.println("扣减失败，库存不够");
            }
        } finally {
            if (Objects.equals(clientId, stringRedisTemplate.opsForValue().get("redisLock"))) {
                // 释放锁
                stringRedisTemplate.delete(this.LOCK_KEY);
            }
        }
        return "success";
    }

    /**
     * 不推荐,单部署下还可以
     * @return
     */
    @GetMapping("/test2")
    public String test2() {
        synchronized (this) {
            Integer count = Integer.parseInt(stringRedisTemplate.opsForValue().get(this.REDIS_LOCK));
            if (count > 0) {
                int realCount = count - 1;
                stringRedisTemplate.opsForValue().set(this.REDIS_LOCK, realCount + "");
                System.out.println("扣减成功，剩余库存：" + realCount);
            } else {
                System.out.println("扣减失败，库存不够");
            }
        }
        return "success" + this.getClass().toString();
    }
}
