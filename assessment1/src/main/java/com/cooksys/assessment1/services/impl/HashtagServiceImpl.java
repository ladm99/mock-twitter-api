package com.cooksys.assessment1.services.impl;

import com.cooksys.assessment1.dtos.HashtagDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.entities.Hashtag;
import com.cooksys.assessment1.entities.Tweet;
import com.cooksys.assessment1.exceptions.BadRequestException;
import com.cooksys.assessment1.mappers.TweetMapper;
import org.springframework.stereotype.Service;

import com.cooksys.assessment1.mappers.HashTagMapper;
import com.cooksys.assessment1.repositories.HashtagRepository;
import com.cooksys.assessment1.services.HashtagService;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashtagServiceImpl implements HashtagService {
    private final HashtagRepository hashtagRepository;
    private final HashTagMapper hashtagMapper;
    private final TweetMapper tweetMapper;

    @Override
    public List<HashtagDto> getHashtags() {
        List<Hashtag> hashtags = hashtagRepository.findAll();

        return hashtagMapper.entitiesToDtos(hashtags);
    }

//    @Override
//    public boolean checkHashTagExist(String label){
//        System.out.println("Label service " + label);
//        List<Hashtag> hashtags = hashtagRepository.findAll();
//        boolean labelExist=false;
//        for(Hashtag hashtag : hashtags){
//            if(hashtag.getLabel() == label) labelExist = true;
//        }
//
//        return labelExist;
//
//    }

    @Override
    public List<TweetResponseDto> getTweetsByHashtag(String label) {
        Optional<Hashtag> tag = hashtagRepository.findByLabel(label);
        if(tag.isEmpty())
            throw new BadRequestException("Hashtag " + label + " does not exist");
        List<Tweet> tweets = tag.get().getTweets();
        Collections.sort(tweets, new Comparator<Tweet>() {
            @Override
            public int compare(Tweet o1, Tweet o2) {
                return o2.getPosted().compareTo(o1.getPosted());
            }
        });
        return tweetMapper.entitiesToResponseDTOs(tweets);
    }
}
