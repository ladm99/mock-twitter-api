package com.cooksys.assessment1.dtos;

import com.cooksys.assessment1.entities.Profile;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class UserResponseDto {

    private String username;
    private Profile profile;
    private Timestamp joined;
    
}
