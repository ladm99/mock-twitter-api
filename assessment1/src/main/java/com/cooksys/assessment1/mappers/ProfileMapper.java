package com.cooksys.assessment1.mappers;

import org.mapstruct.Mapper;

import com.cooksys.assessment1.dtos.ProfileDto;
import com.cooksys.assessment1.entities.Profile;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface ProfileMapper {
    Profile requestDtoEntity(ProfileDto profileDTO);

    ProfileDto entityToDto(Profile profile);
}
