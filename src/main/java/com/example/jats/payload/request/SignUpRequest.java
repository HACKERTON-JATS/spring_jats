package com.example.jats.payload.request;

import com.example.jats.entity.user.enums.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String id;

    private String name;

    private String password;

    private Region region;
}
