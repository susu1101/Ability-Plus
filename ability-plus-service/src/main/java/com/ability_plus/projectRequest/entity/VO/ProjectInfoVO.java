package com.ability_plus.projectRequest.entity.VO;

/**
 * @author sjx
 */
public class ProjectInfoVO {
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
    private Integer createTime;

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
    private Integer lastModifiedTime;

    /**
     * 是否是草稿
     */
    private Boolean isDraft;

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
}
