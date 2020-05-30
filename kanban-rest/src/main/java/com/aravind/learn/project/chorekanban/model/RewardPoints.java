package com.aravind.learn.project.chorekanban.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class RewardPoints {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonIgnore
  private Integer id;
  private Long totalPointsAggregated;
  private Long totalPointsRedeemed;
  private Long currentPoints;

  public void modifyPoints(Long value) {
    if (value >= 0) {
      this.totalPointsAggregated += value;
    }
    else {
      this.totalPointsRedeemed -= value;
    }
    this.currentPoints += value;

  }
}
