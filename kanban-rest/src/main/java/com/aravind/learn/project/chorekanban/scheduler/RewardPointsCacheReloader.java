package com.aravind.learn.project.chorekanban.scheduler;

import com.aravind.learn.project.chorekanban.cache.RewardPointsCache;
import com.aravind.learn.project.chorekanban.service.RewardPointsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RewardPointsCacheReloader {

  @Autowired
  private RewardPointsCache rewardPointsCache;

  @Autowired
  private RewardPointsService rewardPointsService;

  @Scheduled(fixedRateString = "#{${reward-points.cache.reload.rate}}")
  public void reloadRewardPointsCache() {
    log.info("Reloading reward points cache");
    rewardPointsCache.setRewardPoints(rewardPointsService.getRewardPoints());
    log.info("Reloaded reward points cache");
  }
}
