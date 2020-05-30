package com.aravind.learn.project.chorekanban.cache;

import com.aravind.learn.project.chorekanban.model.RewardPoints;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class RewardPointsCache {

  RewardPoints rewardPoints;
}
