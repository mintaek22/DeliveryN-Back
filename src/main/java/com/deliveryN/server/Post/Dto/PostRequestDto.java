package com.deliveryN.server.Post.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    private String name;

    private Integer count;

    private Timestamp deadLine;

    private Double x;

    private Double y;
}
