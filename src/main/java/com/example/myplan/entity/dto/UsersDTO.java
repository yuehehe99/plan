package com.example.myplan.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class UsersDTO {

    private Long id;
    private String name;
    private boolean gender;
}
