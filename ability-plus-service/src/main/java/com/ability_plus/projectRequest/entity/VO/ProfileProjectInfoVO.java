package com.ability_plus.projectRequest.entity.VO;

import lombok.Data;

/**
 * @author pan
 */
@Data
public class ProfileProjectInfoVO {
    private Integer id;
    private String title;
    private String description;
    private String status;
    private Integer lastModifiedTime;
}
