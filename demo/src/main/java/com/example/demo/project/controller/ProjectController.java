package com.example.demo.project.controller;

import com.example.demo.project.dto.ProjectRequest;
import com.example.demo.project.dto.ProjectResponse;
import com.example.demo.project.service.ProjectService;
import com.example.demo.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public ProjectResponse creat(@Valid @RequestBody ProjectRequest projectRequest ,
                                 @AuthenticationPrincipal User user) {
        return projectService.creat(projectRequest , user);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public ProjectResponse findById(@PathVariable Integer id) {
        return projectService.findById(id);
    }

    @GetMapping("")
    public List<ProjectResponse> findAll() {
        return projectService.findAll();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('USER')")
    public ProjectResponse update(@RequestBody ProjectRequest projectRequest) {
        return projectService.update(projectRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void delete(@PathVariable Integer id) {
        projectService.delete(id);
    }


    @GetMapping("user")
    @PreAuthorize("hasAuthority('USER')")
    public List<ProjectResponse> findAllByUser(@AuthenticationPrincipal User user) {
        return projectService.findAllProjectByUser(user);
    }

}