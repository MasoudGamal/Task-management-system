package com.example.demo.task.service;


import com.example.demo.project.entity.Project;
import com.example.demo.project.exception.ProjectNotFoundException;
import com.example.demo.project.repository.ProjectRepository;
import com.example.demo.task.dtos.TaskRequest;
import com.example.demo.task.dtos.TaskResponse;
import com.example.demo.task.entity.Task;
import com.example.demo.task.enums.Status;
import com.example.demo.task.exception.TaskAlreadyAddedToUserException;
import com.example.demo.task.exception.TaskIsDoneException;
import com.example.demo.task.exception.TaskNotFoundException;
import com.example.demo.task.mapper.TaskMapper;
import com.example.demo.task.repository.TaskRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    private final ProjectRepository projectRepository;

    private final TaskMapper taskMapper;

    private final UserRepository userRepository;

    public TaskResponse creatTask(TaskRequest taskRequest) {

        Project project = projectRepository.findAllById(taskRequest.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project Not Found : "));

        Task task = taskMapper.requestToEntity(taskRequest);
        task.setProject(project);
        task.setStatus(Status.TODO);
        taskRepository.save(task);

        project.getTasks().add(task);
        projectRepository.save(project);

        TaskResponse taskResponse = taskMapper.EntityToResponse(task);
        taskResponse.setProjectId(project.getId());
        taskResponse.setStatus(Status.TODO);
        return taskResponse;

    }

    public TaskResponse findById(Integer id){
        Task task = findTaskById(id);

        return taskMapper.EntityToResponse(task);
    }

    public List<TaskResponse> findAll(){
        List<Task> taskList = taskRepository.findAll();
        if (taskList.isEmpty())throw new TaskNotFoundException(" No Tasks : ");

        return taskMapper.entityListToResponseList(taskList);

    }

    public TaskResponse update(TaskRequest taskRequest ,Integer id){

        Task task = findTaskById(id);

        Project project = projectRepository.findAllById(taskRequest.getProjectId())
                        .orElseThrow(() -> new ProjectNotFoundException("Project Not Found  :  "));
        task.setProject(project);
        task.setDescription(taskRequest.getDescription());
        task.setTitle(taskRequest.getTitle());

        taskRepository.save(task);

        return taskMapper.EntityToResponse(task);
    }

    public void delete(Integer id){
        Task task = findTaskById(id);

        taskRepository.delete(task);
    }

    public TaskResponse assignTaskToUser(User user , Integer taskId){

        Task task = findTaskById(taskId);

        boolean taskAlreadyAssigned = user.getTasks()
                .stream()
                .anyMatch(userTask -> userTask.getId() == (task.getId()));

        if (taskAlreadyAssigned) {
            throw new TaskAlreadyAddedToUserException("This task is already assigned to the user.");
        }

        if (task.getStatus() == Status.DONE)throw new TaskIsDoneException("Task Is Done : ");

        user.getTasks().add(task);
        userRepository.save(user);

        task.getUserList().add(user);
        taskRepository.save(task);

        TaskResponse taskResponse =  taskMapper.EntityToResponse(task);
        taskResponse.setProjectId(task.getProject().getId());
        return taskResponse;
    }

    public Task findTaskById(Integer id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task Not Found : "));
    }



}
