package com.example.demo.user.mapper;

import com.example.demo.user.dto.UserRequest;
import com.example.demo.user.dto.UserResponse;
import com.example.demo.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    User requestToEntity(UserRequest userRequest);

    UserResponse EntityToResponse(User user);

    User toUserEntity(@MappingTarget User user,UserRequest userRequest);
}