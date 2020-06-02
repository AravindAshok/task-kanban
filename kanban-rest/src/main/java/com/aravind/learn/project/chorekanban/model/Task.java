package com.aravind.learn.project.chorekanban.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @NotNull
  private String title;
  private String decription;
  @NotNull
  @Enumerated(EnumType.STRING)
  private Status status;
  private LocalDate statusLastModified;
  private LocalDate statusLastDone;
  @NotNull
  @Column(columnDefinition = "varchar(1) default 'N'")
  private Boolean isDailyTask;
  @NotNull
  @Column(columnDefinition = "varchar(1) default 'N'")
  private Boolean isRepeatableTask;
  @Enumerated(EnumType.STRING)
  private Priority priority;
  private Integer points;

}
