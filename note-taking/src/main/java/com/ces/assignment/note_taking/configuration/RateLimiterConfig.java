package com.ces.assignment.note_taking.configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RateLimiterConfig {

    @Value("${rate.limit.capacity:30}")
    int capacity;
    @Value("${rate.limit.time.in.minute:1}")
    int time;

    @Bean
    public Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.classic(capacity, Refill.greedy(capacity, Duration.ofMinutes(time)));
        return Bucket.builder()
            .addLimit(limit)
            .build();
    }
} 