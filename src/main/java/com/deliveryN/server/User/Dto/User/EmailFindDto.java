package com.deliveryN.server.User.Dto.User;

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
