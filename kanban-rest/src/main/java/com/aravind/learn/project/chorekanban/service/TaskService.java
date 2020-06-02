package com.aravind.learn.project.chorekanban.service;

import com.aravind.learn.project.chorekanban.model.Status;
import com.aravind.learn.project.chorekanban.model.Task;
import com.aravind.learn.project.chorekanban.model.TaskTransaction;
import com.aravind.learn.project.chorekanban.repository.TaskRepository;
import com.aravind.learn.project.chorekanban.repository.TaskTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.aravind.learn.project.chorekanban.model.Status.CREATED;
import static com.aravind.learn.project.chorekanban.model.Status.TO_DO;


@Component
@Transactional
public class TaskService {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private TaskTransactionService taskTransactionService;

  public Iterable<Task> getAllTasks() {
    return taskRepository.findAll();
  }

  public Optional<Task> getTaskById(Long id) {
    return taskRepository.findById(id);
  }

  public List<Task> getTaskByStatus(Status status) {
    return taskRepository.findByStatus(status);
  }

  public Task createNewTask(Task newTask) {
    newTask.setStatus(CREATED);
    return taskRepository.save(newTask);
  }

  public Task updateTaskStatus(Long id, Status newStatus) {
    boolean addTransaction = false;
    Task task = taskRepository.findById(id).get();
    if (newStatus != task.getStatus()) {
      if (newStatus == Status.DONE && task.getStatusLastDone() != LocalDate.now()) {
        addTransaction = true;
        task.setStatusLastDone(LocalDate.now());
      }
      task.setStatusLastModified(LocalDate.now());
      task.setStatus(newStatus);
      task = taskRepository.save(task);
//      TODO: uncomment this after TaskTransaction table is fixed
      /*if (addTransaction) {
        taskTransactionService.addTransaction(task);
      }*/
      return task;
    }
    return task;
  }

  public Optional<Task> updateTaskBasicDetails(Long id, Task updatedTask) {
    Optional<Task> taskOptional = getTaskById(id);
    if (taskOptional.isPresent()){
      Task task = taskOptional.get();
      String newTitle = updatedTask.getTitle() != null ? updatedTask.getTitle() : task.getTitle();
      String newDescription = updatedTask.getDecription() != null ? updatedTask.getDecription() : task.getDecription();
      Integer newPoints = updatedTask.getPoints() != null ? updatedTask.getPoints() : task.getPoints();
      Integer newPriority = updatedTask.getPriority() != null ? updatedTask.getPriority() : task.getPriority();

      task.setTitle(newTitle);
      task.setDecription(newDescription);
      task.setPoints(newPoints);
      task.setPriority(newPriority);

      return Optional.of(taskRepository.save(task));
    }
    return Optional.ofNullable(null);
  }

  public List<Task> findRepeatableTasks() {
    return taskRepository.findByIsRepeatableTask(true);
  }

  public void resetDailyTasksStatusToToDo() {
    List<Task> dailyTasks = taskRepository.findByIsDailyTask(true);
    dailyTasks.stream()
        .forEach(task -> {
          task.setStatus(TO_DO);
          task.setStatusLastModified(LocalDate.now());
          taskRepository.save(task);
        });
  }

}
