package com.example.demo.project.service;

import com.example.demo.project.dto.ProjectRequest;
import com.example.demo.project.dto.ProjectResponse;
import com.example.demo.project.entity.Project;
import com.example.demo.project.exception.ProjectAlreadyExistException;
import com.example.demo.project.exception.ProjectNotFoundException;
import com.example.demo.project.mapper.ProjectMapper;


import com.example.demo.project.repository.ProjectRepository;
import com.example.demo.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectMapper projectMapper;

    public ProjectResponse creat(ProjectRequest projectRequest, User user) {

        if (projectRepository.findByName(projectRequest.getName()).isPresent()) {
            throw new ProjectAlreadyExistException("Project Already Exist : ");
        }

        Project project1 = projectMapper.requestToEntity(projectRequest);
        project1.setUser(user);
        projectRepository.save(project1);

        return projectMapper.EntityToResponse(project1);
    }

    public ProjectResponse findById(Integer id) {

        Project project = findProjectById(id);

        ProjectResponse projectResponse = projectMapper.EntityToResponse(project);
        projectResponse.setDescription(project.getDescription());
        projectResponse.setName(project.getName());

        return projectResponse;
    }

    public List<ProjectResponse> findAll() {

        if (projectRepository.findAll().isEmpty()) {
            throw new ProjectNotFoundException("There Are No Projects : ");
        }
        List<Project> projects = projectRepository.findAll();

        return projectMapper.entityToResponse(projects);


    }

    public ProjectResponse update(ProjectRequest projectRequest) {

        Project project1 = findProjectByName(projectRequest.getName());

        project1 = projectMapper.requestToEntity(project1, projectRequest);

        projectRepository.save(project1);

        return projectMapper.EntityToResponse(project1);
    }

    public void delete(Integer id) {

        projectRepository.delete(findProjectById(id));

    }

    public List<ProjectResponse> findAllProjectByUser(User user) {

        if (projectRepository.findAllByUser(user).isEmpty()) {
            throw new ProjectNotFoundException("This User Are No Projects : ");
        }
        List<Project> projects = projectRepository.findAllByUser(user);

        return projectMapper.entityToResponse(projects);

    }

    public Project findProjectById(Integer id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project Not Found : "));
    }

    public Project findProjectByName(String name) {
        return projectRepository.findByName(name)
                .orElseThrow(() -> new ProjectNotFoundException("Project Not Found : "));
    }

}