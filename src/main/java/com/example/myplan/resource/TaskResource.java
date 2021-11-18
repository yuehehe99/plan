package com.example.myplan.resource;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class TaskResource {

    private Long taskId;

    private Long userId;

    @NotBlank(message = "name can not be blank!")
    private String name;

    private String content;

    private String type;
}
