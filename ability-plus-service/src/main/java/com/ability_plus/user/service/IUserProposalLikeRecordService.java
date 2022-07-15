package com.ability_plus.user.service;


import com.ability_plus.user.entity.UserProposalLikeRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
public interface IUserProposalLikeRecordService extends IService<UserProposalLikeRecord> {
    /**
     * 点赞
     * @param proposalId
     * @param http
     */
    public void like(Integer proposalId, HttpServletRequest http);
    /**
     * 取消点赞
     * @param proposalId
     * @param http
     */
    public void unlike(Integer proposalId, HttpServletRequest http);


    /**
     * 获取点赞数
     * @param proposalId
     * @param http
     * @return
     */
    public Integer getLikeNum(Integer proposalId);

}
