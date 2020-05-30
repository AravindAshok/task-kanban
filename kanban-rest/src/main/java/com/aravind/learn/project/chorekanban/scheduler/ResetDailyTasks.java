package com.aravind.learn.project.chorekanban.scheduler;

import com.aravind.learn.project.chorekanban.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ResetDailyTasks {

  @Autowired
  TaskService taskService;

  @Scheduled(cron = "${tasks.daily.reset-time.cron}",zone = "${tasks.daily.reset-time.zone}")
  public void resetDailyTasksStatusToToDo() {
    log.info("Resetting status of daily tasks to TO-DO");
    taskService.resetDailyTasksStatusToToDo();
    log.info("Reset status of daily tasks to TO-DO");
  }
}
