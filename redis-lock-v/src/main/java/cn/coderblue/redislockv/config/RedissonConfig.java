package cn.coderblue.redislockv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author coderblue
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        // 集群
        // Config config = new Config();
        // config.useClusterServers()
        //         .setScanInterval(2000)
        //         .addNodeAddress("redis://127.0.0.1:6379");
        //
        // RedissonClient redisson = Redisson.create(config);
        // return redisson;

        // 单机模式
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(0);
        return  Redisson.create(config);
    }
}