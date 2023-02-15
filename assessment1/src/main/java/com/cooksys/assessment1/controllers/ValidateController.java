package com.cooksys.assessment1.controllers;

import com.cooksys.assessment1.services.ValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validate")
@RequiredArgsConstructor
public class ValidateController {

    private final ValidateService validateService;


    @GetMapping("/username/exists/@{username}")
    public boolean checkUserNameExist(@PathVariable String username){
        return validateService.checkUserNameExist(username);
    }

    @GetMapping("/username/available/@{username}")
    public boolean checkUserNameAvailable(@PathVariable String username){
        return validateService.checkUserNameAvailable(username);
    }

    @GetMapping("/tag/exists/{label}")
    public boolean checkHashTagExist(@PathVariable String label){
        return validateService.checkHashTagExist(label);
    }
}
