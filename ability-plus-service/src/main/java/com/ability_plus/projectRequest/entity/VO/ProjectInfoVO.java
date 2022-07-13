package com.ability_plus.projectRequest.entity.VO;

import lombok.Data;

/**
 * @author sjx
 */
@Data
public class ProjectInfoVO {
    private Integer id;
    private String title;
    private String description;
    private String authorName;
    private Integer authorId;
    private Long lastModifyTime;
    private String status;
}
