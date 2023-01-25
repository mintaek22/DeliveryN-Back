package com.deliveryN.server.Member.Dto.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordCheckDto {

    @NotBlank
    private String password;

    @NotBlank
    private String passwordCheck;
}
