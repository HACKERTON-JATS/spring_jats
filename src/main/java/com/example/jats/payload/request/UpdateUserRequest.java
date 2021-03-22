package com.example.jats.payload.request;

import com.example.jats.entity.user.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private Region region;
}
