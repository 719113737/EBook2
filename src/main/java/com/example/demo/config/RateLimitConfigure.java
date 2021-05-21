package com.example.demo.config;

import com.example.demo.ratelimit.RateLimitInterceptor;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

@Configuration
public class RateLimitConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Refill refill = Refill.greedy(10, Duration.ofMinutes(1));
        Bandwidth limit = Bandwidth.classic(10, refill).withInitialTokens(1);
        Bucket bucket = Bucket4j.builder().addLimit(limit).build();
        registry.addInterceptor(new RateLimitInterceptor(bucket, 1)).addPathPatterns("/user/**");

//        refill = Refill.intervally(3, Duration.ofMinutes(1));
//        limit = Bandwidth.classic(3, refill);
//        bucket = Bucket4j.builder().addLimit(limit).build();
//        registry.addInterceptor(new RateLimitInterceptor(bucket, 1)).addPathPatterns("/place/**");

    }
}
