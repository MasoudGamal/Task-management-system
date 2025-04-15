package com.example.demo.task.controller;

import com.example.demo.task.dtos.TaskRequest;
import com.example.demo.task.dtos.TaskResponse;
import com.example.demo.task.service.TaskService;
import com.example.demo.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponse addTask(@RequestBody @Valid TaskRequest taskRequest) {
        return taskService.creatTask(taskRequest);
    }

    @GetMapping("/{id}")
    public TaskResponse findById(@PathVariable Integer id) {
        return taskService.findById(id);
    }

    @GetMapping
    public List<TaskResponse> findAll() {
        return taskService.findAll();

    }

    @PutMapping("/{id}")
    public TaskResponse update(@RequestBody @Valid TaskRequest taskRequest, @PathVariable Integer id) {

        return taskService.update(taskRequest, id);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        taskService.delete(id);
    }


//    >>  Assign TAsk To User

    @PostMapping("/{taskId}")
    public TaskResponse assignTaskToUser(@AuthenticationPrincipal User user,
                                         @PathVariable Integer taskId) {

        return taskService.assignTaskToUser(user, taskId);

    }


}
