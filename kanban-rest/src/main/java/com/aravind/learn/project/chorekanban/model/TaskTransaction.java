package com.aravind.learn.project.chorekanban.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskTransaction {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;

  @NotNull
  @Column(columnDefinition = "timestamp default current_timestamp")
  private LocalTime createdTime;

  @ManyToOne
  private Task task;

}
