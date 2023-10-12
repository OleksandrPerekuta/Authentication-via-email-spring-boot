package com.authentication.mapper;

import com.authentication.model.User;
import com.authentication.model.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User map(UserDto userDto);
}
