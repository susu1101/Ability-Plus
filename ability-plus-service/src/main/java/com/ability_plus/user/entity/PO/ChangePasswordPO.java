package com.ability_plus.user.entity.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("change password")
public class ChangePasswordPO {
    @ApiModelProperty("new user password")
    private String newPassword;

    @ApiModelProperty("old user password")
    private String oldPassword;
}
