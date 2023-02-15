package com.cooksys.assessment1.services;

import com.cooksys.assessment1.dtos.HashtagDto;
import com.cooksys.assessment1.dtos.TweetResponseDto;
import com.cooksys.assessment1.entities.Hashtag;

import java.util.List;

public interface HashtagService {
    List<HashtagDto> getHashtags();

    List<TweetResponseDto> getTweetsByHashtag(String label);

}
