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
public class ProjectProposalRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 项目id
     */
    private Integer projectId;

    /**
     * 提案id
     */
    private Integer proposalId;

    /**
     * 提案是否被通过
     */
    private Boolean isPick;

    /**
     * 得分
     */
    private Integer rating;

    /**
     * 评论
     */
    private String comment;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }
    public Integer getProposalId() {
        return proposalId;
    }

    public void setProposalId(Integer proposalId) {
        this.proposalId = proposalId;
    }
    public Boolean getIsPick() {
        return isPick;
    }

    public void setIsPick(Boolean isPick) {
        this.isPick = isPick;
    }
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ProjectProposalRecord{" +
            "id=" + id +
            ", projectId=" + projectId +
            ", proposalId=" + proposalId +
            ", isPick=" + isPick +
            ", rating=" + rating +
            ", comment=" + comment +
        "}";
    }
}
