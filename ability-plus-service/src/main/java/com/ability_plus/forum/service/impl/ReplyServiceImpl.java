package com.ability_plus.forum.service.impl;

import com.ability_plus.forum.entity.Post;
import com.ability_plus.forum.entity.Reply;
import com.ability_plus.forum.mapper.ReplyMapper;
import com.ability_plus.forum.service.IPostService;
import com.ability_plus.forum.service.IReplyService;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.POJO.UserPOJO;
import com.ability_plus.utils.CheckUtils;
import com.ability_plus.utils.TimeUtils;
import com.ability_plus.utils.UserUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sjx
 * @since 2022-07-24
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements IReplyService {
    @Autowired
    IPostService postService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void newReply(String data, Integer postId, HttpServletRequest http) {
        Post post = postService.getById(postId);
        CheckUtils.assertNotNull(post,"post not exist");
        Reply reply = new Reply();
        reply.setReplyTime(TimeUtils.getTimeStamp());
        reply.setData(data);
        reply.setReplierId(UserUtils.getCurrentUser(http).getId());
        reply.setPostId(postId);
        this.save(reply);

        post.setHasNewUpdate(true);
        postService.updateById(post);
    }

    @Override
    public void deleteMyReply(Integer replyId, HttpServletRequest http) {
        UserPOJO currentUser = UserUtils.getCurrentUser(http);
        Reply reply = this.getById(replyId);
        checkReplyPermission(currentUser, reply);
        this.removeById(reply);
    }

    private void checkReplyPermission(UserPOJO currentUser, Reply reply) {
        CheckUtils.assertNotNull(reply,"reply not exist");
        if (!reply.getReplierId().equals(currentUser.getId())){
            throw new CheckException("you have no permission to delete others reply");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editMyReply(Integer replyId, String data, HttpServletRequest http) {
        UserPOJO currentUser = UserUtils.getCurrentUser(http);
        Reply reply = this.getById(replyId);
        checkReplyPermission(currentUser, reply);
        reply.setData(data);
        reply.setReplyTime(TimeUtils.getTimeStamp());
        this.updateById(reply);
        Post post = postService.getById(reply.getPostId());
        post.setHasNewUpdate(true);
        postService.updateById(post);
    }
}
