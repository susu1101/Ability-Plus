package com.ability_plus.user.service;


import com.ability_plus.user.entity.UserProposalLikeRecord;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
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

    public Integer likeRecord(Integer proposalId, HttpServletRequest http);

    public boolean canLike(Integer proposalId, Integer studentId);

    public void cancelLikeRecord(Integer proposalId, HttpServletRequest http);

    public boolean canunLike(Integer proposalId, Integer studentId);

}
