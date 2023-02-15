package com.cooksys.assessment1.mappers;

import com.cooksys.assessment1.dtos.HashtagDto;
import com.cooksys.assessment1.entities.Hashtag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HashTagMapper {
    List<HashtagDto> entitiesToDtos(List<Hashtag> hashtags);


}
