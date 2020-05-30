package com.aravind.learn.project.chorekanban.repository;

import com.aravind.learn.project.chorekanban.model.TaskTransaction;
import org.springframework.data.repository.CrudRepository;

public interface TaskTransactionRepository extends CrudRepository<TaskTransaction, String> {
}
