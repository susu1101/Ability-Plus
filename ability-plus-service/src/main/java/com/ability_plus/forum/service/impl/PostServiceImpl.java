package com.ability_plus.forum.service.impl;

import com.ability_plus.forum.entity.*;
import com.ability_plus.forum.mapper.PostMapper;
import com.ability_plus.forum.mapper.ReplyMapper;
import com.ability_plus.forum.service.IPostService;
import com.ability_plus.forum.service.IReplyService;
import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.VO.ProfileProjectInfoVO;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.POJO.UserPOJO;
import com.ability_plus.user.entity.User;
import com.ability_plus.utils.CheckUtils;
import com.ability_plus.utils.TimeUtils;
import com.ability_plus.utils.UserUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.MPJMappingWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    @Resource
    ReplyMapper replyMapper;

    @Resource
    PostMapper postMapper;
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
    public IPage<PostVO> listAllPost(Integer projectId,Integer pageNo, Integer pageSize) {
        MPJLambdaWrapper<Post> wrapper = new MPJLambdaWrapper<>();
        wrapper.leftJoin(User.class,User::getId,Post::getAuthId)
                .leftJoin(ProjectRequest.class,ProjectRequest::getId,Post::getProjectId)
                .leftJoin(Reply.class,Reply::getPostId,Post::getId)

                .eq(Post::getProjectId,projectId);

        wrapper.orderByDesc(Post::getPin,Post::getLastModifiedTime);

        Page<Post> pageSetting = new Page<>(pageNo, pageSize);
//        QueryWrapper<Post> wrapper = new QueryWrapper<>();
//        wrapper.eq("project_id",projectId);
        getPostVOS(wrapper);
        IPage<PostVO> postVOS = postMapper.selectJoinPage(pageSetting, PostVO.class, wrapper);

        return postVOS;
    }

    @Override
    public IPage<PostVO> listMyPost(HttpServletRequest http,Integer pageNo, Integer pageSize) {
        MPJLambdaWrapper<Post> wrapper = new MPJLambdaWrapper<>();
        wrapper.leftJoin(User.class,User::getId,Post::getAuthId)
                .leftJoin(Reply.class,Reply::getPostId,Post::getId)
                .eq(Post::getAuthId, UserUtils.getCurrentUser(http).getId())
                .orderByDesc(Post::getLastModifiedTime);
        Page<Post> pageSetting = new Page<>(pageNo, pageSize);
        getPostVOS(wrapper);
        IPage<PostVO> postVOS = postMapper.selectJoinPage(pageSetting, PostVO.class, wrapper);
        return postVOS;
    }

    @Override
    public List<PostVO> listPostByIds(List<Integer> ids) {
        MPJLambdaWrapper<Post> wrapper = new MPJLambdaWrapper<>();
        wrapper.leftJoin(User.class,User::getId,Post::getAuthId)
                .leftJoin(Reply.class,Reply::getPostId,Post::getId)
                .in(Post::getId,ids)
                .orderByDesc(Post::getLastModifiedTime);
        getPostVOS(wrapper);
        return postMapper.selectJoinList(PostVO.class,wrapper);
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

    @Override
    public List<Integer> newReplyPost(HttpServletRequest http) {
        UserPOJO currentUser = UserUtils.getCurrentUser(http);

        QueryWrapper<Post> wrapper = new QueryWrapper<>();
        wrapper.eq("auth_id",currentUser.getId())
                .eq("has_new_update",true);

        List<Post> list = this.list(wrapper);
        ArrayList<Integer> result = new ArrayList<>();
        for (Post post:list){
            result.add(post.getId());
        }

        return result;
    }

    @Override
    public IPage<ReplyVO> getAPostInfo(Integer postId, Integer pageNo, Integer pageSize,HttpServletRequest http) {
        Page<Reply> pageSetting = new Page<>(pageNo, pageSize);
        MPJLambdaWrapper<Reply> wrapper = new MPJLambdaWrapper<>();
        wrapper.leftJoin(User.class,User::getId,Reply::getReplierId)
                .eq(Reply::getPostId,postId)
                .orderByDesc(Reply::getReplyTime)
                .selectAs(User::getFullName,"replierName")
                .select(Reply::getId)
                .select(Reply::getPostId)
                .select(Reply::getReplyTime)
                .select(Reply::getData)
                .select(Reply::getReplierId)
        ;
        IPage<ReplyVO> replyPages = replyMapper.selectJoinPage(pageSetting, ReplyVO.class, wrapper);
        UserPOJO user = UserUtils.getCurrentUser(http);
        Post post = this.getById(postId);
        if (post.getAuthId().equals(user.getId())){
            post.setHasNewUpdate(false);
            this.updateById(post);
        }
        return replyPages;
    }

    @Override
    public void seeAll(HttpServletRequest http) {
        UserPOJO user = UserUtils.getCurrentUser(http);
        UpdateWrapper<Post> update = new UpdateWrapper<>();
        update.eq("auth_id",user.getId());
        Post post = new Post();
        post.setHasNewUpdate(false);
        this.update(post,update);
    }

    private MPJLambdaWrapper<Post> getPostVOS(MPJLambdaWrapper<Post> wrapper) {
        wrapper.selectAs(Post::getId,"postId")
                .selectAs(Post::getData,"data")
                .selectAs(User::getFullName,"authName")
                .selectAs(Post::getAuthId,"authId")
                .selectAs(Post::getLastModifiedTime,"lastModifiedTime")
                .selectAs(Post::getPin,"isPin")
                .selectAs(Post::getProjectId,"projectId")
                .groupBy(Post::getId)
                .selectCount(Reply::getId,"replyNum")
                .selectAs(ProjectRequest::getName,"projectName")
        ;
        return wrapper;
    }


}
