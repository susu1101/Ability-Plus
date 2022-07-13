package com.ability_plus.system;

import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.ProjectRequestStatus;
import com.ability_plus.projectRequest.service.IProjectRequestService;
import com.ability_plus.utils.TimeUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author sjx
 */
@Configuration
@EnableScheduling
public class ProjectStatusCheckTask {

    @Autowired
    IProjectRequestService projectRequestService;
    @Scheduled(cron = "0 0/1 * * * ?")
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
        UpdateWrapper<ProjectRequest> wrapper = new UpdateWrapper<>();
        long nowTime = TimeUtils.getTimeStamp();
        //not draft
        wrapper.eq("status", ProjectRequestStatus.OPEN_FOR_PROPOSAL);
        wrapper.lt("proposal_ddl",nowTime);
        ProjectRequest updateData = new ProjectRequest();
        updateData.setStatus(ProjectRequestStatus.APPROVING);
        projectRequestService.update(updateData,wrapper);
    }
}
