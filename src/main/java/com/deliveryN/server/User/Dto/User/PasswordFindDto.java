package com.deliveryN.server.User.Dto.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordFindDto {

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

}
