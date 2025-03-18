package com.example.music.config;

import com.example.music.redis.RedisProperties;
import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisProperties properties;

    @Bean
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(properties.getHost())
                .setConnectionMinimumIdleSize(properties.getConnectionMinimumIdleSize())
                .setConnectionPoolSize(properties.getConnectionPollSize())
                .setConnectTimeout(properties.getConnectionTimeout());
        return Redisson.create(config);
    }

}
