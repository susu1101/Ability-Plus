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
    private Long createTime;

    /**
     * 创建用户id
     */
    private Integer creatorId;

    /**
     * 最后修改时间
     */
    private Long lastModifiedTime;

    /**
     * project状态
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

    public Boolean getProcessingDone() {
        return isProcessingDone;
    }

    public void setProcessingDone(Boolean processingDone) {
        isProcessingDone = processingDone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getCanProcess() {
        return canProcess;
    }

    public void setCanProcess(Boolean canProcess) {
        this.canProcess = canProcess;
    }

    public Integer getId() {
        return id;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
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
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", proposalDdl=" + proposalDdl +
                ", solutionDdl=" + solutionDdl +
                ", isProcessingDone=" + isProcessingDone +
                ", createTime=" + createTime +
                ", creatorId=" + creatorId +
                ", lastModifiedTime=" + lastModifiedTime +
                ", status='" + status + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", projectArea='" + projectArea + '\'' +
                ", extraData='" + extraData + '\'' +
                ", canProcess=" + canProcess +
                '}';
    }

    public static Boolean isDraft(ProjectRequest project){
        return ProjectRequestStatus.DRAFT.equals(project.getStatus());
    }
}
