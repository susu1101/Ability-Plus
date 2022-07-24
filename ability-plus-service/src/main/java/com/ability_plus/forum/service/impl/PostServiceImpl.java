package com.ability_plus.forum.service.impl;

import com.ability_plus.forum.entity.Post;
import com.ability_plus.forum.entity.PostVO;
import com.ability_plus.forum.mapper.PostMapper;
import com.ability_plus.forum.service.IPostService;
import com.ability_plus.utils.CheckUtils;
import com.ability_plus.utils.TimeUtils;
import com.ability_plus.utils.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Utilities;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sjx
 * @since 2022-07-24
 */
@Service
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements IPostService {

    @Override
    public void newPost(String data, Integer projectId, Boolean isPin,HttpServletRequest http) {
        Post post = new Post();
        post.setData(data);
        post.setAuthId(UserUtils.getCurrentUser(http).getId());
        if (CheckUtils.isNotNull(isPin)){
            post.setPin(isPin);
        }
        post.setLastModifiedTime(TimeUtils.getTimeStamp());
        post.setProjectId(projectId);
        this.save(post);
    }

    @Override
    public List<PostVO> listAllPost(Integer projectId) {
        QueryWrapper<Post> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id",projectId);
        List<PostVO> postVOS = getPostVOS(wrapper);
        return postVOS;
    }

    @Override
    public List<PostVO> listMyPost(Integer projectId, HttpServletRequest http) {
        QueryWrapper<Post> wrapper = new QueryWrapper<>();
        wrapper.eq("project_id",projectId);
        wrapper.eq("auth_id", UserUtils.getCurrentUser(http).getId());
        List<PostVO> postVOS = getPostVOS(wrapper);
        return postVOS;
    }

    private List<PostVO> getPostVOS(QueryWrapper<Post> wrapper) {
        List<Post> list = this.list(wrapper);
        List<PostVO> postVOS = new ArrayList<>();
        for (Post post : list) {
            PostVO postVO = new PostVO();
            BeanUtils.copyProperties(post, postVO);
            postVOS.add(postVO);
        }
        return postVOS;
    }
}
