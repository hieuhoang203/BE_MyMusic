package com.example.music.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class RedisProperties {

    @Value("${redis.redisson.host}")
    private String host;

    @Value("${redis.redisson.connection-pool-size}")
    private Integer connectionPollSize;

    @Value("${redis.redisson.connection-minimum-idle-size}")
    private Integer connectionMinimumIdleSize;

    @Value("${redis.redisson.connect-timeout}")
    private Integer connectionTimeout;

}
