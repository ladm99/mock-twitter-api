package com.cooksys.assessment1.dtos;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor
@Data

public class CredentialsDto {
    
    private String username;
    
    private String password;
    
}
