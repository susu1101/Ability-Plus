package com.ability_plus.user.service.impl;


import com.ability_plus.system.GlobalExceptionHandler;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.POJO.UserPOJO;
import com.ability_plus.user.entity.StudentFollowing;
import com.ability_plus.user.entity.User;
import com.ability_plus.user.entity.UserProposalLikeRecord;
import com.ability_plus.user.mapper.UserProposalLikeRecordMapper;
import com.ability_plus.user.service.IUserProposalLikeRecordService;
import com.ability_plus.utils.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import com.ability_plus.proposal.entity.Proposal;
import com.ability_plus.proposal.service.IProposalService;

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

    private static final Logger logger = LoggerFactory.getLogger(UserProposalLikeRecordServiceImpl.class);
    @Override
    public Integer likeRecord(Integer proposalId, HttpServletRequest http){

        UserPOJO user = UserUtils.getCurrentUser(http);
        if(user.getIsCompany()){
            throw new CheckException("Company cant like proposal by themselves");
        }
        Integer studentId = user.getId();
        if(canLike(proposalId, studentId)) {
            UserProposalLikeRecord record = new UserProposalLikeRecord();
            record.setStudentId(studentId);
            record.setProposalId(proposalId);
            this.save(record);
            return record.getId();
        }
        else{
            return 0;
        }

    }
    @Override
    public boolean canLike(Integer proposalId, Integer studentId){
        QueryWrapper<UserProposalLikeRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("proposal_id", proposalId);
        wrapper.eq("student_id", studentId);
        List<UserProposalLikeRecord> users = this.list(wrapper);
        if (users.size()>=1){
            logger.warn("already liked");
            return false;
        }
        return true;
    }

    @Override
    public boolean canunLike(Integer proposalId, Integer studentId){
        QueryWrapper<UserProposalLikeRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("proposal_id", proposalId);
        wrapper.eq("student_id", studentId);
        List<UserProposalLikeRecord> users = this.list(wrapper);
        if (users.size() < 1){
            logger.warn("can't unlike");
            return false;
        }
        return true;
    }
    @Override
    public void cancelLikeRecord(Integer proposalId, HttpServletRequest http){
        UserPOJO user = UserUtils.getCurrentUser(http);
        if(user.getIsCompany()){
            throw new CheckException("Company cant unlike proposal by themselves");
        }
        Integer studentId = user.getId();
        if(canunLike(proposalId, studentId)) {
            QueryWrapper<UserProposalLikeRecord> wrapper = findRecord(studentId, proposalId);
            this.remove(wrapper);
        }

    }

    private QueryWrapper<UserProposalLikeRecord> findRecord(Integer studentId, Integer proposalId) {
        QueryWrapper<UserProposalLikeRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentId);
        wrapper.eq("proposal_id", proposalId);
        return wrapper;
    }


}
