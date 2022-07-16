package com.ability_plus.projectRequest.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sjx
 */
@Data
@ApiModel("project information")
public class ProjectInfoVO {
    private Integer id;
    private String title;
    private String description;
    private String authorName;
    private Integer authorId;
    private Long lastModifiedTime;
    private String status;
}
