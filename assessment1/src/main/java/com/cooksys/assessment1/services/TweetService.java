package com.cooksys.assessment1.services;

import com.cooksys.assessment1.dtos.*;
import com.cooksys.assessment1.entities.Credentials;

import java.util.List;

public interface TweetService {

    List<TweetResponseDto> getTweets();

    TweetResponseDto getTweetById(Long id);
    List<UserResponseDto> getTweetLikesById(Long id);
    void likeTweetById(Long id, CredentialsDto credentialsDto);

    TweetResponseDto createTweet(TweetRequestDto tweetRequestDto);

    TweetResponseDto deleteTweetById(Long id, Credentials credentials);

    TweetResponseDto repostTweetById(Long id, Credentials credentials);


    List<UserResponseDto> getMentionsById(Long id);

	List<TweetResponseDto> getTweetReposts(Long id);

	List<TweetResponseDto> getTweetReplies(Long id);

	ContextDto getTweetContext(Long id);

	List<HashtagDto> getTweetTags(Long id);

    TweetResponseDto replyTweetById(Long id, TweetRequestDto tweetRequestDto);
}