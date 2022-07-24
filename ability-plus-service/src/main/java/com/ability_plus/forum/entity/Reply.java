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
public class Reply implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * post id
     */
    private Integer postId;

    /**
     * 帖子回复者id
     */
    private Integer replierId;

    private Long replyTime;

    private String data;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
    public Integer getReplierId() {
        return replierId;
    }

    public void setReplierId(Integer replierId) {
        this.replierId = replierId;
    }
    public Long getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Long replyTime) {
        this.replyTime = replyTime;
    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Reply{" +
            "id=" + id +
            ", postId=" + postId +
            ", replierId=" + replierId +
            ", replyTime=" + replyTime +
            ", data=" + data +
        "}";
    }
}
