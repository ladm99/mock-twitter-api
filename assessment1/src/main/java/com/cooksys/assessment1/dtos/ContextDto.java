package com.cooksys.assessment1.dtos;

import java.util.List;

import com.cooksys.assessment1.entities.Tweet;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Data
public class ContextDto {
	
	private TweetResponseDto target; 
	
	private List<TweetResponseDto> before;
	
	private List<TweetResponseDto> after;

}
