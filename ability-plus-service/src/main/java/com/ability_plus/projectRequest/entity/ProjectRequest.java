package com.ability_plus.projectRequest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
public class ProjectRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getProposalDdl() {
        return proposalDdl;
    }

    public void setProposalDdl(Integer proposalDdl) {
        this.proposalDdl = proposalDdl;
    }
    public Integer getSolutionDdl() {
        return solutionDdl;
    }

    public void setSolutionDdl(Integer solutionDdl) {
        this.solutionDdl = solutionDdl;
    }
    public Boolean getIsProcessingDone() {
        return isProcessingDone;
    }

    public void setIsProcessingDone(Boolean isProcessingDone) {
        this.isProcessingDone = isProcessingDone;
    }
    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }
    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }
    public Integer getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Integer lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
    public Boolean getIsDraft() {
        return isDraft;
    }

    public void setIsDraft(Boolean isDraft) {
        this.isDraft = isDraft;
    }
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    public String getProjectArea() {
        return projectArea;
    }

    public void setProjectArea(String projectArea) {
        this.projectArea = projectArea;
    }
    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    @Override
    public String toString() {
        return "ProjectRequest{" +
            "id=" + id +
            ", name=" + name +
            ", description=" + description +
            ", proposalDdl=" + proposalDdl +
            ", solutionDdl=" + solutionDdl +
            ", isProcessingDone=" + isProcessingDone +
            ", createTime=" + createTime +
            ", creatorId=" + creatorId +
            ", lastModifiedTime=" + lastModifiedTime +
            ", isDraft=" + isDraft +
            ", contactEmail=" + contactEmail +
            ", projectArea=" + projectArea +
            ", extraData=" + extraData +
        "}";
    }
}
