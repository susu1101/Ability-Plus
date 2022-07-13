package com.ability_plus.proposal.service.impl;


import com.ability_plus.projectRequest.entity.ProjectProposalRecord;
import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.ProjectRequestStatus;
import com.ability_plus.projectRequest.service.IProjectProposalRecordService;
import com.ability_plus.projectRequest.service.IProjectRequestService;
import com.ability_plus.proposal.entity.PO.ProposalCreatePO;
import com.ability_plus.proposal.entity.PO.ProposalEditPO;
import com.ability_plus.proposal.entity.Proposal;
import com.ability_plus.proposal.entity.ProposalStatus;
import com.ability_plus.proposal.entity.VO.ProposalInfoVO;
import com.ability_plus.proposal.mapper.ProposalMapper;
import com.ability_plus.proposal.service.IProposalService;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.POJO.UserPOJO;
import com.ability_plus.utils.CheckUtils;
import com.ability_plus.utils.TimeUtils;
import com.ability_plus.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
@Service
public class ProposalServiceImpl extends ServiceImpl<ProposalMapper, Proposal> implements IProposalService {
    @Autowired
    IProjectProposalRecordService projectProposalRecordService;
    @Autowired
    IProjectRequestService projectRequestService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createProposal(ProposalCreatePO po, HttpServletRequest http) {
        UserPOJO user = UserUtils.getCurrentUser(http);

        ProjectRequest project = projectRequestService.getById(po.getProjectId());

        if(!project.getStatus().equals(ProjectRequestStatus.OPEN_FOR_PROPOSAL)){
            throw  new CheckException("This project is no longer accepting proposals");
        }

        Proposal proposal = new Proposal();
        proposal.setTitle(po.getTitle());
        proposal.setCreatorId(user.getId());
        proposal.setOneSentenceDescription(po.getShortDescription());
        if (po.getIsDraft()){
            proposal.setStatus(ProposalStatus.DRAFT);
        }else{
            proposal.setStatus(ProposalStatus.SUBMITTED);
        }
        long notTime = TimeUtils.getTimeStamp();
        proposal.setCreateTime(notTime);
        Map<String, String> extraData = po.getExtraData();
        if (CheckUtils.isNull(extraData)){
            throw new CheckException("extraData cannot be null");
        }
        proposal.setExtraData(JSON.toJSONString(extraData));
        proposal.setLikeNum(0);
        proposal.setLastModifiedTime(notTime);
        this.save(proposal);

        ProjectProposalRecord projectProposalRecord = new ProjectProposalRecord();
        projectProposalRecord.setProjectId(po.getProjectId());
        projectProposalRecord.setProposalId(proposal.getId());
        projectProposalRecord.setIsPick(false);
        projectProposalRecordService.save(projectProposalRecord);


        return proposal.getId();
    }

    @Override
    public Boolean canEditProposal(Integer proposalId,HttpServletRequest http) {
        UserPOJO currentUser = UserUtils.getCurrentUser(http);

        Proposal proposal = this.getById(proposalId);

        return verifyEditProposalPermission(currentUser, proposal);
    }



    @Override
    public void editProposal(ProposalEditPO po,HttpServletRequest http) {
        UserPOJO currentUser = UserUtils.getCurrentUser(http);
        Integer pid = po.getProposalId();
        Proposal proposal = this.getById(pid);
        if (CheckUtils.isNull(proposal)){
            throw new CheckException("proposal not exist");
        }
        verifyEditProposalPermission(currentUser, proposal);
        if (!verifyEditProposalPermission(currentUser,proposal)){
            throw new CheckException("You do not have permission to edit other people's proposals");
        }
        if (CheckUtils.isNotNull(po.getTitle())){
            proposal.setTitle(po.getTitle());
        }

        if (CheckUtils.isNotNull(po.getShortDescription())){
            proposal.setOneSentenceDescription(po.getShortDescription());
        }

        if (CheckUtils.isNotNull(po.getExtraData())){
            proposal.setExtraData(JSON.toJSONString(po.getExtraData()));
        }

        if (CheckUtils.isNotNull(po.getIsDraft())){
            if (po.getIsDraft()){
                proposal.setStatus(ProposalStatus.DRAFT);
            }else {
                proposal.setStatus(ProposalStatus.SUBMITTED);
            }
        }

        updateById(proposal);

    }

    private Boolean verifyEditProposalPermission(UserPOJO currentUser, Proposal proposal) {
        QueryWrapper<ProjectProposalRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("proposal_id",proposal.getId());

        List<ProjectProposalRecord> records = projectProposalRecordService.list(wrapper);
        if (CheckUtils.isEmpty(records)){
            throw new CheckException("proposal not exists");
        }
        ProjectProposalRecord record = records.get(0);
        ProjectRequest proj = projectRequestService.getById(record.getProjectId());
        if(!proj.getStatus().equals(ProjectRequestStatus.OPEN_FOR_PROPOSAL)){
            return false;
        }

        CheckUtils.assertNotNull(proposal,"The proposal you want to edit does not exist");
        return proposal.getCreatorId().equals(currentUser.getId());

    }

    @Override
    public List<ProposalInfoVO> listProposalRequests(String ranking, Boolean isAscendingOrderTime, String searchKey, Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public List<Integer> selectProposal(List<Integer> ids) {
        return null;
    }

    @Override
    public Proposal getProposalInfo(Integer proposalId) { return null; }

    @Override
    public List<ProposalInfoVO> listStudentProposalRequest(Integer creatorId, String status, Boolean isAscendingOrderTime, String searchKey, Integer pageNo, Integer pageSize) { return null; }

    @Override
    public List<ProposalInfoVO> listOutstandingProposalRequest(Boolean isAscendingOrderLike, Boolean isAscendingOrderTime, String searchKey, Integer pageNo, Integer pageSize) { return null; }

}
