package com.example.demo.project.repository;

import com.example.demo.project.entity.Project;
import com.example.demo.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Optional<Project> findByName(@NotBlank String name);

    Optional<Project> findAllById(Integer id);

   List<Project> findAllByUser(User user);
}