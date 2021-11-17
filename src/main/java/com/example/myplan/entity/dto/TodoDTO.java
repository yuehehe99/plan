package com.example.myplan.entity.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class TodoDTO {

    private Long id;
    private Long userId;

    @NotBlank(message = "name can not be blank!")
    private String name;

    private String content;

    private String type;
}
