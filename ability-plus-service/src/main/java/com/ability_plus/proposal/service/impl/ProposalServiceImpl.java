package com.ability_plus.proposal.service.impl;


import com.ability_plus.projectRequest.entity.ProjectProposalRecord;
import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.ProjectRequestStatus;
import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.ability_plus.projectRequest.mapper.ProjectRequestMapper;
import com.ability_plus.projectRequest.service.IProjectProposalRecordService;
import com.ability_plus.projectRequest.service.IProjectRequestService;
import com.ability_plus.proposal.entity.PO.ProposalCreatePO;
import com.ability_plus.proposal.entity.PO.ProposalEditPO;
import com.ability_plus.proposal.entity.Proposal;
import com.ability_plus.proposal.entity.ProposalStatus;
import com.ability_plus.proposal.entity.VO.ProjectProposalInfoVO;
import com.ability_plus.proposal.entity.VO.ProposalInfoVO;
import com.ability_plus.proposal.mapper.ProposalMapper;
import com.ability_plus.proposal.service.IProposalService;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.POJO.UserPOJO;
import com.ability_plus.user.entity.User;
import com.ability_plus.utils.CheckUtils;
import com.ability_plus.utils.TimeUtils;
import com.ability_plus.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
public class ProposalServiceImpl extends MPJBaseServiceImpl<ProposalMapper, Proposal> implements IProposalService {
    @Autowired
    IProjectProposalRecordService projectProposalRecordService;
    @Autowired
    IProjectRequestService projectRequestService;

    @Resource
    ProposalMapper proposalMapper;

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
        proposal.setLastModifiedTime(TimeUtils.getTimeStamp());
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
    public IPage<ProposalInfoVO> listStudentProfileProposals(Integer creatorID, Integer pageNo, Integer pageSize){
        Page<ProposalInfoVO> pageSetting = new Page<>(pageNo, pageSize);
        MPJLambdaWrapper<Proposal> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .leftJoin(User.class,User::getId,Proposal::getCreatorId)
                .leftJoin(ProjectProposalRecord.class,ProjectProposalRecord::getProposalId,Proposal::getId)
                .leftJoin(ProjectRequest.class,ProjectRequest::getId,ProjectProposalRecord::getProjectId)
                .eq(Proposal::getCreatorId,creatorID)
                .eq(Proposal::getStatus,ProposalStatus.APPROVED)
                .select(Proposal::getId)
                .select(Proposal::getTitle)
                .select(Proposal::getOneSentenceDescription)
                .selectAs(ProjectRequest::getName,"projectName")
                .select(Proposal::getStatus)
                .select(Proposal::getLastModifiedTime)
                .orderByAsc(Proposal::getLastModifiedTime);



        IPage<ProposalInfoVO> page=proposalMapper.selectJoinPage(pageSetting,ProposalInfoVO.class,wrapper);

        return page;
    }


    @Override
    public IPage<ProposalInfoVO> listStudentProposal(Integer creatorId, Boolean isAscendingOrderLastModifiedTime, String searchKey, Integer pageNo, Integer pageSize){
        Page<ProposalInfoVO> pageSetting = new Page<>(pageNo, pageSize);
        MPJLambdaWrapper<Proposal> myWrapper = new MPJLambdaWrapper<>();
        myWrapper
                .leftJoin(User.class,User::getId,Proposal::getCreatorId)
                .leftJoin(ProjectProposalRecord.class,ProjectProposalRecord::getProposalId,Proposal::getId)
                .leftJoin(ProjectRequest.class,ProjectRequest::getId,ProjectProposalRecord::getProjectId)
                .eq(Proposal::getCreatorId,creatorId)
                .eq(Proposal::getStatus,ProposalStatus.APPROVED)
                .select(Proposal::getId)
                .select(Proposal::getTitle)
                .select(Proposal::getOneSentenceDescription)
                .selectAs(ProjectRequest::getName,"projectName")
                .select(Proposal::getStatus)
                .select(Proposal::getLastModifiedTime)
                .and(wrapper -> wrapper.like(Proposal::getTitle,"%"+searchKey+"%").or().like(Proposal::getOneSentenceDescription,"%"+searchKey+"%").or().like(User::getFullName,"%"+searchKey+"%"));




        if(isAscendingOrderLastModifiedTime){
            myWrapper.orderByAsc(Proposal::getLastModifiedTime);
        }else{
            myWrapper.orderByDesc(Proposal::getLastModifiedTime);
        }

        IPage<ProposalInfoVO> page=proposalMapper.selectJoinPage(pageSetting,ProposalInfoVO.class,myWrapper);
        return page;
    }

    @Override
    public List<Integer> selectProposal(List<Integer> ids) {
        return null;
    }

    @Override
    public Proposal getProposalInfo(Integer proposalId) { return null; }

    @Override
    public IPage<ProposalInfoVO> listMyProposal(String status, Boolean isAscendingOrder,String whatOrder, String searchKey, Integer pageNo, Integer pageSize,HttpServletRequest http) {
        UserPOJO user = UserUtils.getCurrentUser(http);
        Page<ProposalInfoVO> pageSetting = new Page<>(pageNo, pageSize);
        MPJLambdaWrapper<Proposal> myWrapper = new MPJLambdaWrapper<>();
        myWrapper
                .leftJoin(User.class,User::getId,Proposal::getCreatorId)
                .leftJoin(ProjectProposalRecord.class,ProjectProposalRecord::getProposalId,Proposal::getId)
                .leftJoin(ProjectRequest.class,ProjectRequest::getId,ProjectProposalRecord::getProjectId)
                .eq(Proposal::getCreatorId,user.getId())
                .eq(Proposal::getStatus,status)
                .select(Proposal::getId)
                .select(Proposal::getTitle)
                .select(Proposal::getOneSentenceDescription)
                .selectAs(ProjectRequest::getName,"projectName")
                .select(Proposal::getStatus)
                .select(Proposal::getLastModifiedTime)
                .and(wrapper -> wrapper.like(Proposal::getTitle,"%"+searchKey+"%").or().like(Proposal::getOneSentenceDescription,"%"+searchKey+"%").or().like(User::getFullName,"%"+searchKey+"%"));




        if(whatOrder.equals("LastModifiedTime")){
            if (isAscendingOrder){ myWrapper.orderByAsc(Proposal::getLastModifiedTime);}
            else{myWrapper.orderByDesc(Proposal::getLastModifiedTime); }
        }
        else if(whatOrder.equals("ProposalDue")){
            if(isAscendingOrder){myWrapper.orderByAsc(ProjectRequest::getProposalDdl);}
            else{myWrapper.orderByDesc(ProjectRequest::getProposalDdl);}
        }
        else if(whatOrder.equals("SolutionDue")){
            if(isAscendingOrder){myWrapper.orderByAsc(ProjectRequest::getSolutionDdl);}
            else{myWrapper.orderByDesc(ProjectRequest::getSolutionDdl);}
        }
        else{
            if(isAscendingOrder){myWrapper.orderByAsc(Proposal::getLikeNum);}
            else{myWrapper.orderByDesc(Proposal::getLikeNum);}
        }

        IPage<ProposalInfoVO> page=proposalMapper.selectJoinPage(pageSetting,ProposalInfoVO.class,myWrapper);
        return page;
    }

    @Override
    public List<ProposalInfoVO> listOutstandingProposalRequest(Boolean isAscendingOrderLike, Boolean isAscendingOrderTime, String searchKey, Integer pageNo, Integer pageSize) { return null; }



    @Override
    public IPage<ProjectProposalInfoVO> listProjectProposals(Integer projectId, Boolean isAscendingOrder, String whatOrder, String searchKey, Integer pageNo, Integer pageSize){
        Page<ProjectProposalInfoVO> pageSetting = new Page<>(pageNo, pageSize);
        MPJLambdaWrapper<Proposal> myWrapper = new MPJLambdaWrapper<>();
        myWrapper
                .leftJoin(User.class,User::getId,Proposal::getCreatorId)
                .leftJoin(ProjectProposalRecord.class,ProjectProposalRecord::getProposalId,Proposal::getId)
                .leftJoin(ProjectRequest.class,ProjectRequest::getId,ProjectProposalRecord::getProjectId)
                .eq(ProjectRequest::getId,projectId)
                .ne(Proposal::getStatus,ProposalStatus.DRAFT)
                .select(Proposal::getId)
                .select(Proposal::getTitle)
                .select(Proposal::getOneSentenceDescription)
                .selectAs(User::getId,"authorId")
                .selectAs(User::getFullName,"authorName")
                .select(ProjectProposalRecord::getRating)
                .and(wrapper -> wrapper.like(Proposal::getTitle,"%"+searchKey+"%").or().like(Proposal::getOneSentenceDescription,"%"+searchKey+"%").or().like(User::getFullName,"%"+searchKey+"%"));

        if (whatOrder.equals("Rating")){
            if(isAscendingOrder){myWrapper.orderByAsc(ProjectProposalRecord::getRating);}
            else{myWrapper.orderByDesc(ProjectProposalRecord::getRating);}
        }
        else {
            if(isAscendingOrder){myWrapper.orderByAsc(Proposal::getLastModifiedTime);}
            else{myWrapper.orderByDesc(Proposal::getLastModifiedTime);}
        }


        IPage<ProjectProposalInfoVO> page=proposalMapper.selectJoinPage(pageSetting,ProjectProposalInfoVO.class,myWrapper);
        return page;
    }

    @Override
    public IPage<ProjectProposalInfoVO> listApprovedProjectProposals(Integer projectId, Boolean isAscendingOrder, String searchKey, Integer pageNo, Integer pageSize){
        Page<ProjectProposalInfoVO> pageSetting = new Page<>(pageNo, pageSize);
        MPJLambdaWrapper<Proposal> myWrapper = new MPJLambdaWrapper<>();
        myWrapper
                .leftJoin(User.class,User::getId,Proposal::getCreatorId)
                .leftJoin(ProjectProposalRecord.class,ProjectProposalRecord::getProposalId,Proposal::getId)
                .leftJoin(ProjectRequest.class,ProjectRequest::getId,ProjectProposalRecord::getProjectId)
                .eq(ProjectRequest::getId,projectId)
                .eq(Proposal::getStatus,ProposalStatus.APPROVED)
                .select(Proposal::getId)
                .select(Proposal::getTitle)
                .select(Proposal::getOneSentenceDescription)
                .selectAs(User::getId,"authorId")
                .selectAs(User::getFullName,"authorName")
                .select(ProjectProposalRecord::getRating)
                .and(wrapper -> wrapper.like(Proposal::getTitle,"%"+searchKey+"%").or().like(Proposal::getOneSentenceDescription,"%"+searchKey+"%").or().like(User::getFullName,"%"+searchKey+"%"));

        if (isAscendingOrder){
            myWrapper.orderByAsc(Proposal::getLikeNum);
        }
        else {
            myWrapper.orderByDesc(Proposal::getLikeNum);
        }


        IPage<ProjectProposalInfoVO> page=proposalMapper.selectJoinPage(pageSetting,ProjectProposalInfoVO.class,myWrapper);
        return page;
    }
}
