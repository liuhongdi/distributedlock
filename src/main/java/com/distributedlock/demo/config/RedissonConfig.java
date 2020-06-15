package com.distributedlock.demo.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedissonConfig {

    @Value("${redisson.lock.server.address}")
    private String address;

    @Value("${redisson.lock.server.type}")
    private String type;

    @Value("${redisson.lock.server.password}")
    private String password;

    @Value("${redisson.lock.server.database}")
    private int database;

    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() throws IOException {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+address).setPassword(password).setDatabase(database);

        RedissonClient redisson = Redisson.create(config);

        return redisson;
    }
}

