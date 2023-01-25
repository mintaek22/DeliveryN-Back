package com.deliveryN.server.Member.Dto.Member;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoDto {

    @NotBlank
    private String nickName;
}
