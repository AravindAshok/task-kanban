package com.aravind.learn.project.chorekanban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChoreKanbanApplication {

  public static void main(String[] args) {
    SpringApplication.run(ChoreKanbanApplication.class, args);
  }

}
