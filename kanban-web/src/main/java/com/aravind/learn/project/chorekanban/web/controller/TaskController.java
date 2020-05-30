package com.aravind.learn.project.chorekanban.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TaskController {

  @RequestMapping("/")
  public String index() {
    return "index";
  }
/*
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
*/
}
