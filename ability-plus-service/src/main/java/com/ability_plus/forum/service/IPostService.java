package com.ability_plus.forum.service;

import com.ability_plus.forum.entity.Post;
import com.ability_plus.forum.entity.PostVO;
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
    List<PostVO> listAllPost(Integer projectId);

    /**
     * list my post
     * @param projectId
     * @param http
     * @return
     */
    List<PostVO> listMyPost(Integer projectId, HttpServletRequest http);
}
