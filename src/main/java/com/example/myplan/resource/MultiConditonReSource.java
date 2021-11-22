package com.example.myplan.resource;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class MultiConditonReSource {

    private Long taskId;

    private Long userId; //多条件查询不能查外键？

    @NotBlank(message = "name can not be blank!")
    private String name;

    private String content;

    private String type;
    private boolean deleted;

    private int pageNumber;
    private int pageSize;
}