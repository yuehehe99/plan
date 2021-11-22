package com.example.myplan.resource;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MultiConditonReSource {

    private Long taskId;

    private Long userId;

    private String name;

    private String content;

    private String type;
    private boolean deleted;

    private int pageNumber;
    private int pageSize;
}