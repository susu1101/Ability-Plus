package com.ability_plus.forum.service.impl;

import com.ability_plus.forum.entity.Post;
import com.ability_plus.forum.entity.PostVO;
import com.ability_plus.forum.entity.Reply;
import com.ability_plus.forum.mapper.PostMapper;
import com.ability_plus.forum.service.IPostService;
import com.ability_plus.forum.service.IReplyService;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.utils.CheckUtils;
import com.ability_plus.utils.TimeUtils;
import com.ability_plus.utils.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    IReplyService replyService;
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMyPost(Integer postId, HttpServletRequest http) {
        Post post = this.getById(postId);
        checkPostModifyPermission(http, post);
        this.removeById(post);

        QueryWrapper<Reply> wrapper = new QueryWrapper<>();
        wrapper.eq("post_id",post.getId());
        List<Reply> list = replyService.list(wrapper);
        for (Reply reply:list){
            replyService.removeById(reply);
        }
    }

    private void checkPostModifyPermission(HttpServletRequest http, Post post) {
        CheckUtils.assertNotNull(post,"post not exist");
        if (!post.getAuthId().equals(UserUtils.getCurrentUser(http).getId())){
            throw new CheckException("you have no permission to delete others project");
        }
    }

    @Override
    public void editMyPost(Integer postId, String data,Boolean isPick, HttpServletRequest http) {
        Post post = this.getById(postId);
        checkPostModifyPermission(http, post);
        if (CheckUtils.isNotNull(isPick)){
            post.setPin(isPick);
        }
        post.setData(data);
        post.setLastModifiedTime(TimeUtils.getTimeStamp());
        this.updateById(post);
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
