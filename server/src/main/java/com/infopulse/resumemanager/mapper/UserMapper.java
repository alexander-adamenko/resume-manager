package com.infopulse.resumemanager.mapper;

import com.infopulse.resumemanager.record.UserDto;
import com.infopulse.resumemanager.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    //UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    UserDto userToUserDto(User user);
}
