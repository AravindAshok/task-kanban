package com.aravind.learn.project.chorekanban.service;

import com.aravind.learn.project.chorekanban.cache.RewardPointsCache;
import com.aravind.learn.project.chorekanban.model.RewardPoints;
import com.aravind.learn.project.chorekanban.repository.RewardPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RewardPointsService {

  @Autowired
  private RewardPointsRepository rewardPointsRepository;

  @Autowired
  private RewardPointsCache rewardPointsCache;

  public RewardPoints getRewardPoints() {
    return rewardPointsRepository.findAll().iterator().next();
  }

  public RewardPoints getCachedRewardPoints() {
    return rewardPointsCache.getRewardPoints();
  }

  public RewardPoints updateRewardPoints(RewardPoints updatedRewardPoints) {
    rewardPointsCache.setRewardPoints(updatedRewardPoints);
    return rewardPointsRepository.save(updatedRewardPoints);
  }
}
