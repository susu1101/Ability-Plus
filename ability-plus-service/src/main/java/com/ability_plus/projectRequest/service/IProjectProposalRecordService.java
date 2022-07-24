package com.ability_plus.projectRequest.service;


import com.ability_plus.projectRequest.entity.ProjectProposalRecord;
import com.ability_plus.projectRequest.entity.VO.CommentInfoVO;
import com.ability_plus.proposal.entity.Proposal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.base.MPJBaseService;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
public interface IProjectProposalRecordService extends IService<ProjectProposalRecord>, MPJBaseService<ProjectProposalRecord> {
    /**
     * getProjectIdByProposalId
     * @param proposalId
     * @return  projectId if exist
     *          -1 if record not exist
     */
    public Integer getProjectIdByProposalId(Integer proposalId);

    /**
     * get comment info vo
     * @param proposalId
     * @param projectId
     * @return
     */
    CommentInfoVO getInfo(Integer proposalId, Integer projectId);

    }
