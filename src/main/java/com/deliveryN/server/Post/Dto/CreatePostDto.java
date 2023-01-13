package com.deliveryN.server.Post.Dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDto {

    @NotBlank
    private String name;

    @NotNull
    private Integer count;

    //01131230
    @NotBlank
    private String time;

    @NotNull
    private Double x;

    @NotNull
    private Double y;

}
