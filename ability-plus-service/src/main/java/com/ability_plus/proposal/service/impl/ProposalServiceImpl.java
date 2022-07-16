package com.ability_plus.proposal.service.impl;


import com.ability_plus.projectRequest.entity.ProjectProposalRecord;
import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.ProjectRequestStatus;
import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.ability_plus.projectRequest.mapper.ProjectRequestMapper;
import com.ability_plus.projectRequest.service.IProjectProposalRecordService;
import com.ability_plus.projectRequest.service.IProjectRequestService;
import com.ability_plus.proposal.entity.P2pPOJO;
import com.ability_plus.proposal.entity.PO.ProposalBatchProcessRequest;
import com.ability_plus.proposal.entity.PO.ProposalCreatePO;
import com.ability_plus.proposal.entity.PO.ProposalEditPO;
import com.ability_plus.proposal.entity.Proposal;
import com.ability_plus.proposal.entity.ProposalIds;
import com.ability_plus.proposal.entity.ProposalStatus;
import com.ability_plus.proposal.entity.VO.*;
import com.ability_plus.proposal.mapper.ProposalMapper;
import com.ability_plus.proposal.service.IProposalService;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.POJO.UserPOJO;
import com.ability_plus.user.entity.User;
import com.ability_plus.user.service.IUserService;
import com.ability_plus.utils.CardUtils;
import com.ability_plus.utils.CheckUtils;
import com.ability_plus.utils.TimeUtils;
import com.ability_plus.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
@Service
public class ProposalServiceImpl extends MPJBaseServiceImpl<ProposalMapper, Proposal> implements IProposalService {
    @Autowired
    IProjectProposalRecordService projectProposalRecordService;
    @Autowired
    IProjectRequestService projectRequestService;

    @Autowired
    IUserService userService;
    @Resource
    ProposalMapper proposalMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer createProposal(ProposalCreatePO po, HttpServletRequest http) {
        UserPOJO user = UserUtils.getCurrentUser(http);

        ProjectRequest project = projectRequestService.getById(po.getProjectId());

        if (!project.getStatus().equals(ProjectRequestStatus.OPEN_FOR_PROPOSAL)) {
            throw new CheckException("This project is no longer accepting proposals");
        }

        Proposal proposal = new Proposal();
        proposal.setTitle(po.getTitle());
        proposal.setCreatorId(user.getId());
        proposal.setOneSentenceDescription(po.getShortDescription());
        if (po.getIsDraft()) {
            proposal.setStatus(ProposalStatus.DRAFT);
        } else {
            proposal.setStatus(ProposalStatus.SUBMITTED);
        }
        long notTime = TimeUtils.getTimeStamp();
        proposal.setCreateTime(notTime);
        Map<String, String> extraData = po.getExtraData();
        if (CheckUtils.isNull(extraData)) {
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
    public Boolean canEditProposal(Integer proposalId, HttpServletRequest http) {
        UserPOJO currentUser = UserUtils.getCurrentUser(http);

        Proposal proposal = this.getById(proposalId);

        return verifyEditProposalPermission(currentUser, proposal);
    }


    @Override
    public void editProposal(ProposalEditPO po, HttpServletRequest http) {
        UserPOJO currentUser = UserUtils.getCurrentUser(http);
        Integer pid = po.getProposalId();
        Proposal proposal = this.getById(pid);
        if (CheckUtils.isNull(proposal)) {
            throw new CheckException("proposal not exist");
        }
        verifyEditProposalPermission(currentUser, proposal);
        if (!verifyEditProposalPermission(currentUser, proposal)) {
            throw new CheckException("You do not have permission to edit other people's proposals");
        }
        if (CheckUtils.isNotNull(po.getTitle())) {
            proposal.setTitle(po.getTitle());
        }

        if (CheckUtils.isNotNull(po.getShortDescription())) {
            proposal.setOneSentenceDescription(po.getShortDescription());
        }

        if (CheckUtils.isNotNull(po.getExtraData())) {
            proposal.setExtraData(JSON.toJSONString(po.getExtraData()));
        }

        if (CheckUtils.isNotNull(po.getIsDraft())) {
            if (po.getIsDraft()) {
                proposal.setStatus(ProposalStatus.DRAFT);
            } else {
                proposal.setStatus(ProposalStatus.SUBMITTED);
            }
        }
        proposal.setLastModifiedTime(TimeUtils.getTimeStamp());
        updateById(proposal);

    }

    private Boolean verifyEditProposalPermission(UserPOJO currentUser, Proposal proposal) {
        QueryWrapper<ProjectProposalRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("proposal_id", proposal.getId());

        List<ProjectProposalRecord> records = projectProposalRecordService.list(wrapper);
        if (CheckUtils.isEmpty(records)) {
            throw new CheckException("proposal not exists");
        }
        ProjectProposalRecord record = records.get(0);
        ProjectRequest proj = projectRequestService.getById(record.getProjectId());
        if (!proj.getStatus().equals(ProjectRequestStatus.OPEN_FOR_PROPOSAL)) {
            return false;
        }

        CheckUtils.assertNotNull(proposal, "The proposal you want to edit does not exist");
        return proposal.getCreatorId().equals(currentUser.getId());

    }

    @Override
    public List<ProposalInfoVO> listProposalRequests(String ranking, Boolean isAscendingOrderTime, String searchKey, Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public IPage<ProposalInfoVO> listStudentProfileProposals(Integer creatorId, Integer pageNo, Integer pageSize) {
        Page<ProposalInfoVO> pageSetting = new Page<>(pageNo, pageSize);
        MPJLambdaWrapper<Proposal> wrapper = CardUtils.appendToProposalCardWrapper(new MPJLambdaWrapper<>());
        wrapper.eq(Proposal::getCreatorId, creatorId)
                .eq(Proposal::getStatus, ProposalStatus.APPROVED)
                .orderByAsc(Proposal::getLastModifiedTime);
        IPage<ProposalInfoVO> page = proposalMapper.selectJoinPage(pageSetting, ProposalInfoVO.class, wrapper);

        return page;
    }


    @Override
    public IPage<ProposalInfoVO> listStudentProposal(Integer creatorId, Boolean isAscendingOrderLastModifiedTime, String searchKey, Integer pageNo, Integer pageSize) {
        Page<ProposalInfoVO> pageSetting = new Page<>(pageNo, pageSize);
        MPJLambdaWrapper<Proposal> myWrapper = CardUtils.appendToProposalCardWrapper(new MPJLambdaWrapper<>());
        myWrapper
                .eq(Proposal::getCreatorId, creatorId)
                .eq(Proposal::getStatus, ProposalStatus.APPROVED)
                .select(Proposal::getStatus)
                .and(wrapper -> wrapper.like(Proposal::getTitle, "%" + searchKey + "%").or().like(Proposal::getOneSentenceDescription, "%" + searchKey + "%").or().like(User::getFullName, "%" + searchKey + "%"));

        if (isAscendingOrderLastModifiedTime) {
            myWrapper.orderByAsc(Proposal::getLastModifiedTime);
        } else {
            myWrapper.orderByDesc(Proposal::getLastModifiedTime);
        }

        IPage<ProposalInfoVO> page = proposalMapper.selectJoinPage(pageSetting, ProposalInfoVO.class, myWrapper);
        return page;
    }



    @Override
    public ProposalDetailVO getProposalInfo(Integer proposalId) {
        Proposal proposal = this.getById(proposalId);
        CheckUtils.assertNotNull(proposal,"proposal not exist");
        ProposalDetailVO data = new ProposalDetailVO();
        BeanUtils.copyProperties(proposal,data);
        User auth = userService.getById(proposal.getCreatorId());
        data.setCreatorName(auth.getFullName());

        return data;
    }

    @Override
    public IPage<StudentMyProposalVO> listMyProposal(String status, Boolean isAscendingOrder, String whatOrder, String searchKey, Integer pageNo, Integer pageSize, HttpServletRequest http) {
        UserPOJO user = UserUtils.getCurrentUser(http);
        Page<StudentMyProposalVO> pageSetting = new Page<>(pageNo, pageSize);


        MPJLambdaWrapper<Proposal> myWrapper = CardUtils.appendToProposalCardWrapper(new MPJLambdaWrapper<>());
        myWrapper.select(Proposal::getStatus)
                .eq(Proposal::getCreatorId, user.getId())
                .eq(Proposal::getStatus, status);
//                .and(wrapper -> wrapper.like(Proposal::getTitle, "%" + searchKey + "%").or().like(Proposal::getOneSentenceDescription, "%" + searchKey + "%").or().like(User::getFullName, "%" + searchKey + "%"));
        //Todo like有错
        setStudentMyProposalOrder(isAscendingOrder, whatOrder, myWrapper);
        IPage<StudentMyProposalVO> page = proposalMapper.selectJoinPage(pageSetting, StudentMyProposalVO.class, myWrapper);
        return page;
    }

    private void setStudentMyProposalOrder(Boolean isAscendingOrder, String whatOrder, MPJLambdaWrapper<Proposal> myWrapper) {
        if ("LastModifiedTime".equals(whatOrder)) {
            if (isAscendingOrder) {
                myWrapper.orderByAsc(Proposal::getLastModifiedTime);
            } else {
                myWrapper.orderByDesc(Proposal::getLastModifiedTime);
            }
        } else if ("ProposalDue".equals(whatOrder)) {
            if (isAscendingOrder) {
                myWrapper.orderByAsc(ProjectRequest::getProposalDdl);
            } else {
                myWrapper.orderByDesc(ProjectRequest::getProposalDdl);
            }
        } else if ("SolutionDue".equals(whatOrder)) {
            if (isAscendingOrder) {
                myWrapper.orderByAsc(ProjectRequest::getSolutionDdl);
            } else {
                myWrapper.orderByDesc(ProjectRequest::getSolutionDdl);
            }
        } else {
            if (isAscendingOrder) {
                myWrapper.orderByAsc(Proposal::getLikeNum);
            } else {
                myWrapper.orderByDesc(Proposal::getLikeNum);
            }
        }
    }

    @Override
    public IPage<ProposalCard> listOutstandingProposal(Boolean isAscendingOrderLike,
                                                       Boolean isAscendingOrderTime,
                                                       String searchKey, Integer pageNo,
                                                       Integer pageSize) {
        Page<Proposal> pageSetting = new Page<>(pageNo, pageSize);
        setFilter(isAscendingOrderLike, isAscendingOrderTime, pageSetting);

        MPJLambdaWrapper<Proposal> w = CardUtils.appendToProposalCardWrapper(new MPJLambdaWrapper<>());
        w.and(wrapper -> wrapper.like(Proposal::getTitle, "%" + searchKey + "%").or().like(Proposal::getOneSentenceDescription, "%" + searchKey + "%").or().like(User::getFullName, "%" + searchKey + "%"));
        IPage<ProposalCard> page = proposalMapper.selectJoinPage(pageSetting, ProposalCard.class, w);
        return page;
    }

    private void setFilter(Boolean isAscendingOrderLike, Boolean isAscendingOrderTime, Page<Proposal> pageSetting) {
        if (isAscendingOrderLike) {
            pageSetting.addOrder(OrderItem.asc("like_num"));
        } else {
            pageSetting.addOrder(OrderItem.desc("like_num"));
        }
        if (isAscendingOrderTime) {
            pageSetting.addOrder(OrderItem.asc("last_modified_time"));
        } else {
            pageSetting.addOrder(OrderItem.desc("last_modified_time"));
        }
    }


    @Override
    public IPage<ProjectProposalInfoVO> listProjectProposals(Integer projectId, Boolean isAscendingOrder, String whatOrder, String searchKey, Integer pageNo, Integer pageSize) {
        Page<ProjectProposalInfoVO> pageSetting = new Page<>(pageNo, pageSize);
        MPJLambdaWrapper<Proposal> myWrapper = CardUtils.appendToProposalCardWrapper(new MPJLambdaWrapper<>());
        myWrapper
                .eq(ProjectRequest::getId, projectId)
                .ne(Proposal::getStatus, ProposalStatus.DRAFT)
                .select(ProjectProposalRecord::getRating)
                .and(wrapper -> wrapper.like(Proposal::getTitle, "%" + searchKey + "%").or().like(Proposal::getOneSentenceDescription, "%" + searchKey + "%").or().like(User::getFullName, "%" + searchKey + "%"));

        if (whatOrder.equals("Rating")) {
            if (isAscendingOrder) {
                myWrapper.orderByAsc(ProjectProposalRecord::getRating);
            } else {
                myWrapper.orderByDesc(ProjectProposalRecord::getRating);
            }
        } else {
            if (isAscendingOrder) {
                myWrapper.orderByAsc(Proposal::getLastModifiedTime);
            } else {
                myWrapper.orderByDesc(Proposal::getLastModifiedTime);
            }
        }


        IPage<ProjectProposalInfoVO> page = proposalMapper.selectJoinPage(pageSetting, ProjectProposalInfoVO.class, myWrapper);
        return page;
    }

    @Override
    public IPage<ProjectProposalInfoVO> listApprovedProjectProposals(Integer projectId, Boolean isAscendingOrder, String searchKey, Integer pageNo, Integer pageSize) {
        Page<ProjectProposalInfoVO> pageSetting = new Page<>(pageNo, pageSize);
        MPJLambdaWrapper<Proposal> myWrapper = CardUtils.appendToProposalCardWrapper(new MPJLambdaWrapper<>());
        myWrapper
                .eq(ProjectRequest::getId, projectId)
                .eq(Proposal::getStatus, ProposalStatus.APPROVED)
                .select(ProjectProposalRecord::getRating)
                .and(wrapper -> wrapper.like(Proposal::getTitle, "%" + searchKey + "%").or().like(Proposal::getOneSentenceDescription, "%" + searchKey + "%").or().like(User::getFullName, "%" + searchKey + "%"));

        if (isAscendingOrder) {
            myWrapper.orderByAsc(Proposal::getLikeNum);
        } else {
            myWrapper.orderByDesc(Proposal::getLikeNum);
        }


        IPage<ProjectProposalInfoVO> page = proposalMapper.selectJoinPage(pageSetting, ProjectProposalInfoVO.class, myWrapper);
        return page;
    }

    @Override
    public void deleteProposal(Integer proposalId,HttpServletRequest http) {
        Proposal proposal = this.getById(proposalId);
        UserPOJO currentUser = UserUtils.getCurrentUser(http);
        CheckUtils.assertNotNull(proposal,"proposal not exist");
        if (!Proposal.isDraft(proposal)){
            throw  new CheckException("this proposal is already submitted");
        }
        if (!currentUser.getId().equals(proposal.getCreatorId())){
            throw new CheckException("you cannot delete others proposal");
        }
        this.removeById(proposal);

    }

    @Override
    public void batchProcessProposals(ProposalBatchProcessRequest request, HttpServletRequest http) {
        ArrayList<Integer> ids = request.getIds();
        String status = request.getStatus();
        MPJLambdaWrapper<Proposal> wrapper = new MPJLambdaWrapper<>();
        UserPOJO user = UserUtils.getCurrentUser(http);
        wrapper
                .leftJoin(ProjectProposalRecord.class,ProjectProposalRecord::getProposalId,Proposal::getId)
                .leftJoin(ProjectRequest.class,ProjectRequest::getId,ProjectProposalRecord::getProjectId)
                .in(Proposal::getId,ids)
                .selectAs(ProjectRequest::getStatus,"projectStatus")
                .selectAs(ProjectRequest::getCreatorId,"projectCreatorId")
                .selectAs(Proposal::getId,"proposalCreateId");
        List<P2pPOJO> p2pPOJOS = proposalMapper.selectJoinList(P2pPOJO.class, wrapper);

        for (P2pPOJO p2pPOJO:p2pPOJOS){
            if (!user.getId().equals(p2pPOJO.getProjectCreatorId())){
                throw new CheckException("Not all proposals belong in your project");
            }
            if (!ProjectRequestStatus.APPROVING.equals(p2pPOJO.getProjectStatus())){
                throw new CheckException("Not all proposals belong in the processing stage");
            }
        }

        UpdateWrapper<ProjectProposalRecord> updateWrapper = new UpdateWrapper<>();
        updateWrapper.in("id",ids);
        Proposal proposal = new Proposal();
        proposal.set(status);
        this.update();

    }
}
