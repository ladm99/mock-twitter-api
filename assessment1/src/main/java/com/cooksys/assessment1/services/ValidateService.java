package com.cooksys.assessment1.services;

public interface ValidateService {
    boolean checkUserNameExist(String username);

    boolean checkUserNameAvailable(String username);


    boolean checkHashTagExist(String label);
}
