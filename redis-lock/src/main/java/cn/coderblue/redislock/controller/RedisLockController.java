package cn.coderblue.redislock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

@RestController
@RequestMapping("/lock")
public class RedisLockController {

    @Autowired
    private RedisLockRegistry redisLockRegistry;

    private final Logger log = LoggerFactory.getLogger(RedisLockController.class);

    @GetMapping("/test1")
    public void test1() {
        Lock lock = redisLockRegistry.obtain("redis");
        try {
            //尝试在指定时间内加锁，如果已经有其他锁锁住，获取当前线程不能加锁，则返回false，加锁失败；加锁成功则返回true
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                log.info("lock is ready");
                TimeUnit.SECONDS.sleep(5);
            }
        } catch (InterruptedException e) {
            log.error("obtain lock error", e);
        } finally {
            lock.unlock();
            log.info("lock is unlock");
        }
    }

    @GetMapping("/test2")
    public void test2() {
        Lock lock = redisLockRegistry.obtain("redis");
        try {
            //尝试在指定时间内加锁，如果已经有其他锁锁住，获取当前线程不能加锁，则返回false，加锁失败；加锁成功则返回true
            if (lock.tryLock(3, TimeUnit.SECONDS)) {
                log.info("lock is ready");
                TimeUnit.SECONDS.sleep(7);
            }
        } catch (InterruptedException e) {
            log.error("obtain lock error", e);
        } finally {
            lock.
                    unlock();
            log.info("lock is unlock");
        }
    }
}
