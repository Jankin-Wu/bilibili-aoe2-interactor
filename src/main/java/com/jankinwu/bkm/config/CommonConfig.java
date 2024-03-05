package com.jankinwu.bkm.config;

import com.jankinwu.bkm.handler.CustomRejectedExecutionHandler;
import com.jankinwu.bkm.hints.BulletHandlerRuntimeHints;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;

/**
 * @author jankinwu
 * @description
 * @date 2024/3/2 1:25
 */
@Configuration
public class CommonConfig {

    @Bean
    public BulletHandlerRuntimeHints hintsRegistrar() {
        return new BulletHandlerRuntimeHints();
    }

    @Value("${app.scheduled.queue-capacity:100}")
    private int queueCapacity;

    @Bean("taskQueue")
    public BlockingQueue<Runnable> taskQueue() {
        return new LinkedBlockingQueue<>(queueCapacity);
    }

    @Bean("customRejectionPolicy")
    public RejectedExecutionHandler customRejectionPolicy() {
        return new CustomRejectedExecutionHandler();
    }

}
