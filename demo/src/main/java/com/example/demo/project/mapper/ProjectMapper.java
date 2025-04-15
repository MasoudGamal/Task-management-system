package com.example.demo.project.mapper;

import com.example.demo.project.dto.ProjectRequest;
import com.example.demo.project.dto.ProjectResponse;
import com.example.demo.project.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {

    Project requestToEntity(ProjectRequest projectRequest);

    ProjectResponse EntityToResponse(Project project);

    List<ProjectResponse> entityToResponse(List<Project> projects);

    Project requestToEntity(@MappingTarget Project project , ProjectRequest projectRequest);

}