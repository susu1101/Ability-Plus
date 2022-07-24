package com.ability_plus.user.entity.VO;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Ziqi Ding
 */
@Data
@ApiModel("student followings information")
public class StudentFollowingVO {
    private String companyName;
    private Integer companyId;
    private Integer followTime;
    private Integer openingProjectNum;
}
