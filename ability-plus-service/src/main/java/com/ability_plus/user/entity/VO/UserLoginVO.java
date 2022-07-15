package com.ability_plus.user.entity.VO;

import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * @author Ziqi Ding
 */

@Data
@ApiModel("student login information")
public class UserLoginVO {
    private String accessToken;
    private Boolean isCompany;
    private String userName;
}
