package com.ability_plus.user.entity.PO;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.w3c.dom.Text;

import java.util.Map;

@Data
@ApiModel("edit profile")
public class UserProfileEditPO {

    @ApiModelProperty("id of user")
    private Integer userId;

    @ApiModelProperty("user name")
    private String userName;

    @ApiModelProperty("extra data in json")
    private Map<String,String> extraData;

    @ApiModelProperty("user password")
    private String password;

}
