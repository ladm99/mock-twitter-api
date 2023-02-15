package com.cooksys.assessment1.dtos;

import com.cooksys.assessment1.entities.Credentials;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TweetRequestDto {

    private String content;

    private CredentialsDto credentials;

}
