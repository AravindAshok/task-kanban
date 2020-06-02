package com.aravind.learn.project.chorekanban.repository;


import com.aravind.learn.project.chorekanban.model.Status;
import com.aravind.learn.project.chorekanban.model.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

  public List<Task> findByStatus(Status status);

  public List<Task> findByIsRepeatableTaskAndStatus(Boolean isRepeatableTask, Status status);

  public List<Task> findByIsDailyTask(Boolean isDailyTask);
}
