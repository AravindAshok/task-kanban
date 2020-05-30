package com.aravind.learn.project.chorekanban.config;

import com.aravind.learn.project.chorekanban.scheduler.RewardPointsCacheReloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Autowired
  RewardPointsCacheReloader rewardPointsCacheReloader;

  @Bean
  public CommandLineRunner loadRewardPointsCache() {
    return new CommandLineRunner() {
      @Override
      public void run(String... args) throws Exception {
        System.out.println("Reloading cache on application startup.");
        rewardPointsCacheReloader.reloadRewardPointsCache();
      }
    };
  }

}
