package com.cooksys.assessment1.services.impl;

import com.cooksys.assessment1.entities.Hashtag;
import com.cooksys.assessment1.entities.User;
import com.cooksys.assessment1.repositories.HashtagRepository;
import com.cooksys.assessment1.repositories.UserRepository;
import com.cooksys.assessment1.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {
    private final UserRepository userRepository;
    private final HashtagRepository hashtagRepository;
    @Override
    public boolean checkUserNameExist(String username){
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        return  !user.isEmpty();
    }

    @Override
    public boolean checkUserNameAvailable(String username){
        Optional<User> user = userRepository.findByCredentialsUsernameAndDeletedFalse(username);
        return  user.isEmpty();
    }
    @Override
    public boolean checkHashTagExist(String label){
        List<Hashtag> hashtags = hashtagRepository.findAll();
        boolean labelExist=false;
        for(Hashtag hashtag : hashtags){
            if(hashtag.getLabel().equals(label))
                labelExist = true;
        }
        return labelExist;
    }

}
