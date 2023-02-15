package com.cooksys.assessment1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequestDto {
    private CredentialsDto credentials;
    private ProfileDto profile;

}
