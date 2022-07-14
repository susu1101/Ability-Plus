package com.ability_plus.projectRequest.entity.VO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sjx
 * 比project实体类多一个company_name
 * 少一个id
 */
@Data
@ApiModel("project information")
public class ProjectDetailInfoVO {
    /**
     * 项目名
     */
    private String name;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 接受提案ddl
     */
    private Integer proposalDdl;

    /**
     * 接受答案ddl
     */
    private Integer solutionDdl;

    /**
     * 公司是否处理完成
     */
    private Boolean isProcessingDone;

    /**
     * 创建项目时间
     */
    private Long createTime;

    /**
     * 创建用户id
     */
    private Integer creatorId;

    /**
     * 公司名
     */
    private String creatorName;

    /**
     * 最后修改时间
     */
    private Long lastModifiedTime;

    /**
     * 状态
     */
    private String status;

    /**
     * 公司邮箱，若未特别设置，则为公司自己的邮箱
     */
    private String contactEmail;

    /**
     * 项目领域
     */
    private String projectArea;

    /**
     * 额外数据
     */
    private String extraData;

    /**
     * 是否可以处理
     */
    private Boolean canProcess;


}
