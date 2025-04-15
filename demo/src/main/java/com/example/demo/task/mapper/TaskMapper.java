package com.example.demo.task.mapper;

import com.example.demo.task.dtos.TaskRequest;
import com.example.demo.task.dtos.TaskResponse;
import com.example.demo.task.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskMapper {

    Task requestToEntity (TaskRequest taskRequest);

    TaskResponse EntityToResponse (Task task);

    List<TaskResponse> entityListToResponseList(List<Task> tasks);

}