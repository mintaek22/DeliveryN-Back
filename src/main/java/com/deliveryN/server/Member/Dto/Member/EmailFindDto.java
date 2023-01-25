package com.deliveryN.server.Member.Dto.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailFindDto {

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

}
