package com.cooksys.assessment1.dtos;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class HashtagDto {
        @Column(unique=true)
        private String label;
        private Timestamp firstUsed;
        private Timestamp lastUsed;
}
