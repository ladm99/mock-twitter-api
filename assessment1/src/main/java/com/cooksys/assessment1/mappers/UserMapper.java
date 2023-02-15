package com.cooksys.assessment1.mappers;

import java.util.List;

import com.cooksys.assessment1.dtos.UserRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;
import com.cooksys.assessment1.entities.User;

@Mapper(componentModel = "spring", uses = {ProfileMapper.class, CredentialsMapper.class})
public interface UserMapper {
	
	List<UserResponseDto> entitiesToResponseDTOs(List<User> users);

		User userRequestDtoToEntity(UserRequestDto userRequestDto);
	
	@Mapping(target = "username", source = "credentials.username") //insert the value from credentials.username into the "username" field of response DTO
	UserResponseDto entityToResponseDto(User user);


	User requestDtoToEntity(UserRequestDto userRequestDto);

}