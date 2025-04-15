package com.example.demo.task.dtos;

import com.example.demo.task.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponse {

    private int id;
    private String title;
    private String description;
    private int projectId;
    private Status status;


}
