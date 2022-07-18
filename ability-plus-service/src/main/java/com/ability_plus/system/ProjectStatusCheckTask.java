package com.ability_plus.system;

import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.ProjectRequestStatus;
import com.ability_plus.projectRequest.service.IProjectRequestService;
import com.ability_plus.proposal.entity.Proposal;
import com.ability_plus.proposal.entity.ProposalStatus;
import com.ability_plus.proposal.service.IProposalService;
import com.ability_plus.utils.TimeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author sjx
 */
@Configuration
@EnableScheduling
public class ProjectStatusCheckTask {

    @Autowired
    IProjectRequestService projectRequestService;
    @Autowired
    IProposalService proposalService;

    @Scheduled(cron = "0 0/1 * * * ?")
    @Transactional(rollbackFor = Exception.class)
    public void checkStatusTask(){
        checkProposalDue();
        checkSolutionDue();
    }

    private void checkSolutionDue() {
        UpdateWrapper<ProjectRequest> wrapper = new UpdateWrapper<>();
        long nowTime = TimeUtils.getTimeStamp();
        //not draft
        wrapper.eq("status", ProjectRequestStatus.OPEN_FOR_SOLUTION);
        wrapper.lt("solution_ddl",nowTime);
        ProjectRequest updateData = new ProjectRequest();
        updateData.setStatus(ProjectRequestStatus.CLOSED);
        projectRequestService.update(updateData,wrapper);
    }


    private void checkProposalDue() {
        QueryWrapper<ProjectRequest> wrapper = new QueryWrapper<>();
        long nowTime = TimeUtils.getTimeStamp();
        wrapper.eq("status", ProjectRequestStatus.OPEN_FOR_PROPOSAL);
        wrapper.lt("proposal_ddl",nowTime);
        List<ProjectRequest> projectNeedChange = projectRequestService.list(wrapper);
        if (projectNeedChange.size()==0){
            return;
        }
        for (ProjectRequest project:projectNeedChange){
            UpdateWrapper<Proposal> updateWrapper = new UpdateWrapper<>();
            updateWrapper.ne("status", ProposalStatus.DRAFT);
            updateWrapper.inSql("id","select id proposal_id from project_proposal_record where project_id="+project.getId().toString());

            Proposal proposal = new Proposal();
            proposal.setStatus(ProposalStatus.APPROVING);
            proposalService.update(proposal,updateWrapper);

            project.setStatus(ProjectRequestStatus.APPROVING);
            projectRequestService.updateById(project);
        }


//        UpdateWrapper<ProjectRequest> wrapper = new UpdateWrapper<>();
//        long nowTime = TimeUtils.getTimeStamp();
//        //not draft
//        wrapper.eq("status", ProjectRequestStatus.OPEN_FOR_PROPOSAL);
//        wrapper.lt("proposal_ddl",nowTime);
//        ProjectRequest updateData = new ProjectRequest();
//        updateData.setStatus(ProjectRequestStatus.APPROVING);
//        projectRequestService.update(updateData,wrapper);
//
    }
}
