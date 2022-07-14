package com.ability_plus.user.service.impl;


import com.ability_plus.system.GlobalExceptionHandler;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.POJO.UserPOJO;
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
    public Integer likeRecord(Integer proposalId, HttpServletRequest http){
        logger.info("111111");
        UserPOJO user = UserUtils.getCurrentUser(http);
        if(user.getIsCompany()){
            throw new CheckException("Company cant like proposal by themselves");
        }
        Integer studentId = user.getId();
        logger.info("here we go");
        logger.info(studentId.toString());

        UserProposalLikeRecord record = new UserProposalLikeRecord();
        record.setStudentId(studentId);
        record.setProposalId(proposalId);
        this.save(record);
        return record.getId();
    }


}
