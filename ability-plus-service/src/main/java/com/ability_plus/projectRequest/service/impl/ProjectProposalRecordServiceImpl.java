package com.ability_plus.projectRequest.service.impl;


import com.ability_plus.projectRequest.entity.ProjectProposalRecord;
import com.ability_plus.projectRequest.mapper.ProjectProposalRecordMapper;
import com.ability_plus.projectRequest.service.IProjectProposalRecordService;
import com.ability_plus.proposal.service.IProposalService;
import com.ability_plus.user.entity.User;
import com.ability_plus.user.service.IUserService;
import com.ability_plus.utils.CheckUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.base.MPJBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
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
public class ProjectProposalRecordServiceImpl extends MPJBaseServiceImpl<ProjectProposalRecordMapper, ProjectProposalRecord> implements IProjectProposalRecordService {

    @Override
    public Integer getProjectIdByProposalId(Integer proposalId) {
        QueryWrapper<ProjectProposalRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("proposal_id",proposalId);
        List<ProjectProposalRecord> list = this.list(wrapper);
        if (CheckUtils.isNotEmpty(list)){
            return list.get(0).getProjectId();
        }
        return -1;
    }
}
