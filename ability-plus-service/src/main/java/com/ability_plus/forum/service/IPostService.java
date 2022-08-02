package com.ability_plus.forum.service;

import com.ability_plus.forum.entity.Post;
import com.ability_plus.forum.entity.PostVO;
import com.ability_plus.forum.entity.Reply;
import com.ability_plus.forum.entity.ReplyVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author sjx
 * @since 2022-07-24
 */
public interface IPostService extends IService<Post> {
    /**
     * post a new post
     * @param data
     * @param projectId
     * @param http
     */
    void newPost(String data, Integer projectId,Boolean isPin, HttpServletRequest http);

    /**
     * list all post
     * @param projectId
     * @return
     */
    IPage<PostVO> listAllPost(Integer projectId,Integer pageNo, Integer pageSize);

    /**
     * list my post
     * @param projectId
     * @param http
     * @return
     */
    IPage<PostVO> listMyPost(HttpServletRequest http,Integer pageNo, Integer pageSize);

    /**
     * delete my post
     * @param postId
     * @param http
     */
    void deleteMyPost(Integer postId,HttpServletRequest http);


    /**
     * edit a post
     * @param postId
     * @param data
     * @param http
     */
    void editMyPost(Integer postId,String data,Boolean isPick, HttpServletRequest http);

    /**
     * return all my post which has new reply
     * @param http
     * @return
     */
    List<Integer> newReplyPost(HttpServletRequest http);

    IPage<ReplyVO> getAPostInfo(Integer postId, Integer pageNo, Integer pageSize);
}
