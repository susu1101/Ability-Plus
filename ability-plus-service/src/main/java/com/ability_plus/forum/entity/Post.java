package com.ability_plus.forum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author sjx
 * @since 2022-07-24
 */
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * project id
     */
    private Integer projectId;

    /**
     * 帖子创建者id
     */
    private Integer authId;

    private Long lastModifiedTime;

    private Boolean pin;

    private Boolean hasNewUpdate;

    private String data;

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
    public Integer getAuthId() {
        return authId;
    }

    public void setAuthId(Integer authId) {
        this.authId = authId;
    }
    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
    public Boolean getPin() {
        return pin;
    }

    public void setPin(Boolean pin) {
        this.pin = pin;
    }
    public Boolean getHasNewUpdate() {
        return hasNewUpdate;
    }

    public void setHasNewUpdate(Boolean hasNewUpdate) {
        this.hasNewUpdate = hasNewUpdate;
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Post{" +
            "id=" + id +
            ", projectId=" + projectId +
            ", authId=" + authId +
            ", lastModifiedTime=" + lastModifiedTime +
            ", pin=" + pin +
            ", hasNewUpdate=" + hasNewUpdate +
            ", data=" + data +
        "}";
    }
}
