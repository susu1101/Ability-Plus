package com.ability_plus.user.service.impl;


import com.ability_plus.proposal.entity.Proposal;
import com.ability_plus.proposal.service.IProposalService;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.POJO.UserPOJO;
import com.ability_plus.user.entity.UserProposalLikeRecord;
import com.ability_plus.user.mapper.UserProposalLikeRecordMapper;
import com.ability_plus.user.service.IUserProposalLikeRecordService;
import com.ability_plus.utils.CheckUtils;
import com.ability_plus.utils.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
@Service
public class UserProposalLikeRecordServiceImpl extends ServiceImpl<UserProposalLikeRecordMapper, UserProposalLikeRecord> implements IUserProposalLikeRecordService {

    @Autowired
    IProposalService proposalService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void like(Integer proposalId, HttpServletRequest http) {
        Proposal proposal = proposalService.getById(proposalId);
        CheckUtils.assertNotNull(proposal,"proposal not exist");

        UserPOJO user = UserUtils.getCurrentUser(http);
        if(alreadyLike(proposalId, user)){
            throw new CheckException("you are already like this proposal");
        }

        UserProposalLikeRecord data = new UserProposalLikeRecord();
        data.setProposalId(proposalId);
        data.setStudentId(user.getId());
        this.save(data);

        proposal.setLikeNum(proposal.getLikeNum()+1);
        proposalService.updateById(proposal);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlike(Integer proposalId, HttpServletRequest http) {
        Proposal proposal = proposalService.getById(proposalId);
        CheckUtils.assertNotNull(proposal,"proposal not exist");
        UserPOJO user = UserUtils.getCurrentUser(http);
        QueryWrapper<UserProposalLikeRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", user.getId())
                .eq("proposal_id", proposalId);
        List<UserProposalLikeRecord> records = this.list(wrapper);

        if (records.size()==0){
            throw new CheckException("you are not already like this proposal");
        }
        this.remove(wrapper);

        if (proposal.getLikeNum()==0){
            return;
        }
        proposal.setLikeNum(proposal.getLikeNum()-1);
        proposalService.updateById(proposal);
    }

    @Override
    public Integer getLikeNum(Integer proposalId) {
        Proposal proposal = proposalService.getById(proposalId);
        CheckUtils.assertNotNull(proposal,"proposal not exist");

        return proposal.getLikeNum();

    }

    @Override
    public Boolean alreadyLike(Integer proposal, HttpServletRequest http) {
        UserPOJO currentUser = UserUtils.getCurrentUser(http);
        return alreadyLike(proposal,currentUser);

    }


    private Boolean alreadyLike(Integer proposalId, UserPOJO user) {
        QueryWrapper<UserProposalLikeRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", user.getId())
                .eq("proposal_id", proposalId);
        List<UserProposalLikeRecord> records = this.list(wrapper);
        if (CheckUtils.isNull(records) || records.size()==0){
            return false;
        }
        return true;
    }

}
