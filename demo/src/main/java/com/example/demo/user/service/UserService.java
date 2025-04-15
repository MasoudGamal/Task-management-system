package com.example.demo.user.service;

import com.example.demo.user.exception.RoleNotFoundException;
import com.example.demo.task.dtos.StatusRequest;
import com.example.demo.task.dtos.TaskResponse;
import com.example.demo.task.entity.Task;
import com.example.demo.task.enums.Status;
import com.example.demo.task.exception.TaskNotFoundException;
import com.example.demo.task.mapper.TaskMapper;
import com.example.demo.task.repository.TaskRepository;
import com.example.demo.user.dto.UserRequest;
import com.example.demo.user.dto.UserResponse;
import com.example.demo.user.entity.User;
import com.example.demo.user.exception.InvalidTaskStatusUpdateException;
import com.example.demo.user.exception.TaskNotBelongToUserException;
import com.example.demo.user.exception.UserAlreadyExistsException;
import com.example.demo.user.exception.UserNotFoundException;
import com.example.demo.user.mapper.UserMapper;
import com.example.demo.user.entity.Role;
import com.example.demo.user.repository.RoleRepository;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;


    public UserResponse create(UserRequest userRequest) {

        if (userRepository.findByName(userRequest.getName()).isPresent())
            throw new UserAlreadyExistsException("User Already Exists  : ");

        User user1 = userMapper.requestToEntity(userRequest);

        Role role = roleRepository.findByRole(userRequest.getRole())
                .orElseThrow(() -> new RoleNotFoundException("Role Not Found : "));
        user1.getRoles().add(role);

        user1.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        userRepository.save(user1);

        return userMapper.EntityToResponse(user1);
    }

    public UserResponse findById(Integer id) {

        User user = findUserById(id);

        return userMapper.EntityToResponse(user);
    }

    public List<UserResponse> findAll() {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) throw new UserNotFoundException("There are no Users  : ");

        return users.stream().map(user -> {
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setName(user.getName());
            userResponse.setEmail(user.getEmail());
            return userResponse;
        }).toList();
    }

    public void delete(Integer id) {
        User user = findUserById(id);
        userRepository.delete(user);
    }

    public UserResponse update(UserRequest userRequest) {

        User user = findUserByName(userRequest.getName());

        user = userMapper.toUserEntity(user, userRequest);

        user = userRepository.save(user);

        return userMapper.EntityToResponse(user);

    }

    public TaskResponse updateStatusForUser(User user, Integer taskId, StatusRequest statusRequest) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task Not Found : "));

        boolean taskBelong = user.getTasks()
                .stream()
                .anyMatch(userTask -> userTask.getId() == (task.getId()));

        if (!taskBelong) {
            throw new TaskNotBelongToUserException("This task does not belong to the user.");
        }

        if (task.getStatus() == Status.TODO && statusRequest.getStatus() == Status.IN_PROGRESS
                || task.getStatus() == Status.IN_PROGRESS && statusRequest.getStatus() == Status.DONE) {
            task.setStatus(statusRequest.getStatus());
            taskRepository.save(task);
        } else {
            throw new InvalidTaskStatusUpdateException("Cannot update task status from "
                    + task.getStatus() + " to " + statusRequest.getStatus());
        }
        TaskResponse taskResponse = taskMapper.EntityToResponse(task);
        taskResponse.setProjectId(task.getProject().getId());
        return taskResponse;

    }

    public User findUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Not Found : "));
    }

    public User findUserByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException("User Not Found : "));
    }


}
