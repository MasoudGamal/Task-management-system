package com.example.demo.user.controller;

import com.example.demo.task.dtos.StatusRequest;
import com.example.demo.task.dtos.TaskResponse;
import com.example.demo.task.enums.Status;
import com.example.demo.user.dto.UserRequest;
import com.example.demo.user.dto.UserResponse;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponse create(@Valid @RequestBody UserRequest userRequest) {
        return userService.create(userRequest);
    }

    @GetMapping("/{id}")
    public UserResponse findById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

    @PutMapping
    public UserResponse update(@Valid @RequestBody UserRequest userRequest) {
        return userService.update(userRequest);
    }

    @PutMapping("status/{taskId}")
    public TaskResponse updateStatusForUser(@AuthenticationPrincipal User user
                                          , @PathVariable Integer taskId
                                          , @Valid @RequestBody StatusRequest statusRequest) {

        return userService.updateStatusForUser(user, taskId, statusRequest);

    }
}
