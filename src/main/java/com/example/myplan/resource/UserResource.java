package com.example.myplan.resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResource {

    private Long userId;
    private String name;
    private String password;
    private String authority;
    private boolean gender;
}
