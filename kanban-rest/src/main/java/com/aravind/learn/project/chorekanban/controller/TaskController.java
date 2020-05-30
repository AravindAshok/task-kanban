package com.aravind.learn.project.chorekanban.controller;

import com.aravind.learn.project.chorekanban.model.RewardPoints;
import com.aravind.learn.project.chorekanban.model.Status;
import com.aravind.learn.project.chorekanban.model.Task;
import com.aravind.learn.project.chorekanban.service.RewardPointsService;
import com.aravind.learn.project.chorekanban.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.aravind.learn.project.chorekanban.model.Status.DONE;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

  @Autowired
  private TaskService taskService;

  @Autowired
  private RewardPointsService rewardPointsService;

  @GetMapping("/")
  public @ResponseBody Iterable<Task> getAllTasks() {
    return taskService.getAllTasks();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
    return taskService.getTaskById(id)
        .map(task -> ResponseEntity.ok(task))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/tasksByStatus")
  public @ResponseBody List<Task> getTaskByStatus(@RequestParam Status status) {
    return taskService.getTaskByStatus(status);
  }

  @PostMapping(value = "/")
  public @ResponseBody Task addTask(@RequestBody Task newTask) {
    return taskService.createNewTask(newTask);
  }

  @PostMapping(value = "/{id}/updateStatus")
  public Task updateTaskStatus(@PathVariable Long id, @RequestParam(name = "status") Status newStatus) {
    return taskService.updateTaskStatus(id, newStatus);
  }

  @PostMapping(value = "/{id}/markAsDone")
  public Task updateTaskStatusToDone(@PathVariable Long id) {
    return taskService.updateTaskStatus(id, DONE);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
    return taskService.updateTaskBasicDetails(id, updatedTask)
        .map(task -> ResponseEntity.ok(task))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping("/rewardPoints")
  public RewardPoints getRewardPoints() {
    return rewardPointsService.getCachedRewardPoints();
  }

  @GetMapping("/common")
  public List<Task> findCommonTasks() {
    return taskService.findRepeatableTasks();
  }
}
