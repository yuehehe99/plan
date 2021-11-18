package com.example.myplan.resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResource {

    private Long id;
    private String name;
    private boolean gender;
}
