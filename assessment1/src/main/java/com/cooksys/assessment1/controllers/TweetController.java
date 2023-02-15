package com.cooksys.assessment1.controllers;

import java.util.List;

import com.cooksys.assessment1.dtos.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.assessment1.entities.Credentials;
import com.cooksys.assessment1.services.TweetService;
import com.cooksys.assessment1.services.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/tweets")
@RequiredArgsConstructor
public class TweetController {

    private final TweetService tweetService;
    private final UserService userService;

    @GetMapping
    public List<TweetResponseDto> getTweets(){

        return tweetService.getTweets();
    }
    @GetMapping("/{id}")
    public TweetResponseDto getTweetById(@PathVariable(name="id") Long id){
        return tweetService.getTweetById(id);
    }
    @GetMapping("/{id}/likes")
    public List<UserResponseDto> getTweetLikesById(@PathVariable(name="id") Long id){
        return tweetService.getTweetLikesById(id);
    }

    @GetMapping("/{id}/reposts")
    public List<TweetResponseDto> getTweetReposts(@PathVariable(name="id") Long id){
        return tweetService.getTweetReposts(id); 
    }
    
    @GetMapping("/{id}/replies")
    public List<TweetResponseDto> getTweetReplies(@PathVariable(name="id") Long id){
        return tweetService.getTweetReplies(id); 
    }
    
    @GetMapping("/{id}/context")
    public ContextDto getTweetContext(@PathVariable(name="id") Long id){
        return tweetService.getTweetContext(id); 
    }
    
    @GetMapping("/{id}/tags")
    public List<HashtagDto> getTweetTags(@PathVariable(name="id") Long id){
        return tweetService.getTweetTags(id); 
    }
    
    @PostMapping("/{id}/like")
    public void likeTweetById(@PathVariable(name="id") Long id, @RequestBody CredentialsDto credentialsDto){
        tweetService.likeTweetById(id, credentialsDto);
    }

    @PostMapping
    public TweetResponseDto createTweet(@RequestBody TweetRequestDto tweetRequestDto){
        return tweetService.createTweet(tweetRequestDto);
    }

    @DeleteMapping("/{id}")
    public TweetResponseDto deleteTweetById(@PathVariable(name="id") Long id,@RequestBody Credentials credentials){
        return tweetService.deleteTweetById(id,credentials);
    }
    @PostMapping("{id}/repost")
    public TweetResponseDto repostTweetById(@PathVariable(name="id") Long id,@RequestBody Credentials credentials){
        return tweetService.repostTweetById(id, credentials);
    }
    @GetMapping("/{id}/mentions")
    public List<UserResponseDto> getMentionsById(@PathVariable(name="id") Long id){
        return tweetService.getMentionsById(id);
    }

    @PostMapping("/{id}/reply")
    public TweetResponseDto replyTweetById(@PathVariable Long id, @RequestBody TweetRequestDto tweetRequestDto){
        return tweetService.replyTweetById(id, tweetRequestDto);
    }

}