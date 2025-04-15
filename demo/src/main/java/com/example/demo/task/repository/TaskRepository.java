package com.example.demo.task.repository;

import com.example.demo.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    Optional<Task> findAllById(Integer id);

}
