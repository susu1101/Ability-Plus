package com.ability_plus.proposal.entity;

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
public class Proposal implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 学生id
     */
    private Integer creatorId;

    /**
     * 提案内容，json格式
     */
    private String extraData;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 创建时间
     */
    private Integer likeNum;

    /**
     * 最后修改时间
     */
    private Long lastModifiedTime;

    /**
     * proposal状态
     */
    private String status;

    /**
     * 是否是草稿
     */
    private String oneSentenceDescription;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }
    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOneSentenceDescription() {
        return oneSentenceDescription;
    }

    public void setOneSentenceDescription(String oneSentenceDescription) {
        this.oneSentenceDescription = oneSentenceDescription;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creatorId=" + creatorId +
                ", description='" + extraData + '\'' +
                ", createTime=" + createTime +
                ", likeNum=" + likeNum +
                ", lastModifiedTime=" + lastModifiedTime +
                ", status='" + status + '\'' +
                ", oneSentenceDescription='" + oneSentenceDescription + '\'' +
                '}';
    }
}
