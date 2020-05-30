package com.aravind.learn.project.chorekanban.service;

import com.aravind.learn.project.chorekanban.model.RewardPoints;
import com.aravind.learn.project.chorekanban.model.Task;
import com.aravind.learn.project.chorekanban.model.TaskTransaction;
import com.aravind.learn.project.chorekanban.repository.TaskTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskTransactionService {

  @Autowired
  private RewardPointsService rewardPointsService;

  @Autowired
  private TaskTransactionRepository taskTransactionRepository;

  public TaskTransaction addTransaction(Task task) {
    TaskTransaction taskTransaction = new TaskTransaction();
    taskTransaction.setTask(task);

    RewardPoints rewardPoints = rewardPointsService.getRewardPoints();
    rewardPoints.modifyPoints(Long.valueOf(task.getPoints()));
    rewardPointsService.updateRewardPoints(rewardPoints);

    return taskTransactionRepository.save(taskTransaction);
  }
}
