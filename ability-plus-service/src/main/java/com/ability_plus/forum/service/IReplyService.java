package com.ability_plus.forum.service;

import com.ability_plus.forum.entity.Reply;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sjx
 * @since 2022-07-24
 */
public interface IReplyService extends IService<Reply> {
    /**
     * new a reply to a post
     * @param data
     * @param postId
     * @param http
     */
    void newReply(String data,Integer postId, HttpServletRequest http);

    /**
     * delete my reply
     * @param replyId
     * @param http
     */
    void deleteMyReply(Integer replyId, HttpServletRequest http);

    /**
     * edit my reply
     * @param replyId
     * @param data
     * @param http
     */
    void editMyReply(Integer replyId, String data,HttpServletRequest http);
}
