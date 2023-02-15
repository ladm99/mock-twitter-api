package com.cooksys.assessment1.dtos;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ProfileDto {
    private String firstName;

    private String lastName;

    private String email;

    private String phone;
}
