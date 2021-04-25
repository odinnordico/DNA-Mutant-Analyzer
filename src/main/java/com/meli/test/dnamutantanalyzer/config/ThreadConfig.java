package com.meli.test.dnamutantanalyzer.config;

import java.util.concurrent.Executor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * ThreadConfig exports the configuration of the Threads and Concurrence
 */
@Configuration
public class ThreadConfig {

  /**
   * taskExecutor defines the {@link Executor} used in the platform
   *
   * @return the bean {@link Executor}
   */
  @Bean
  public Executor taskExecutor() {
    int cores = NumberUtils.max(Runtime.getRuntime().availableProcessors(), 2);
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(cores);
    executor.setMaxPoolSize(cores * 2);
    executor.setQueueCapacity(1000000);
    executor.setThreadNamePrefix("DNA-");
    executor.initialize();
    return executor;
  }
}
