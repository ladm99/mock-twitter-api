package com.cooksys.assessment1.services;

import com.cooksys.assessment1.dtos.CredentialsDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.dtos.UserRequestDto;
import com.cooksys.assessment1.dtos.UserResponseDto;

import java.util.List;

public interface UserService {

	List<UserResponseDto> getUsers();


    UserResponseDto createUser(UserRequestDto userRequestDto);

	void followUser(CredentialsDto credentialsRequestDto, String username);

	void unfollowUser(CredentialsDto credentialsRequestDto, String username);

	UserResponseDto updateUsername(UserRequestDto userRequestDto, String username);


	UserResponseDto getUser(String username);


	List<TweetResponseDto> getUserMentions(String username);


	List<TweetResponseDto> getUserTweets(String username);


	List<TweetResponseDto> getUserFeed(String username);


	UserResponseDto deleteUser(CredentialsDto credentials, String username);

    List<UserResponseDto> getFollowers(String username);

	List<UserResponseDto> getFollowing(String username);
}