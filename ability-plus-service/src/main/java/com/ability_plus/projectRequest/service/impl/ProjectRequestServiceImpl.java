package com.ability_plus.projectRequest.service.impl;


import com.ability_plus.projectRequest.entity.PO.ProjectCreatePO;
import com.ability_plus.projectRequest.entity.PO.ProjectEditPO;
import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.ProjectRequestStatus;
import com.ability_plus.projectRequest.entity.VO.ProfileProjectInfoVO;
import com.ability_plus.projectRequest.entity.VO.ProjectDetailInfoVO;
import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.ability_plus.projectRequest.mapper.ProjectRequestMapper;
import com.ability_plus.projectRequest.service.IProjectRequestService;
import com.ability_plus.proposal.entity.Proposal;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.system.entity.FilterName;
import com.ability_plus.user.entity.User;
import com.ability_plus.user.entity.POJO.UserPOJO;
import com.ability_plus.user.service.IUserService;
import com.ability_plus.utils.CheckUtils;
import com.ability_plus.utils.TimeUtils;
import com.ability_plus.utils.UserUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ability_plus.system.entity.FilterName;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
public class ProjectRequestServiceImpl extends MPJBaseServiceImpl<ProjectRequestMapper, ProjectRequest> implements IProjectRequestService {

    @Autowired
    IUserService userService;

    @Resource
    ProjectRequestMapper projectRequestMapper;

    /**
     * 公司创建project
     * @param po
     * @param http
     * @return
     */
    @Override
    public Integer createProjectRequest(ProjectCreatePO po, HttpServletRequest http) {
        //check input
        CheckUtils.assertNotNull(po,"input cannot be null");
        UserPOJO user = UserUtils.getCurrentUser(http);
        if (!user.getIsCompany()){
            throw new CheckException("Students do not have permission to post projects");
        }
        long notTime = TimeUtils.getTimeStamp();
        Map<String, String> extraData = po.getExtraData();
        String description = extraData.get("description");
        //check description
        CheckUtils.assertNotNull(description,"Description cannot be null");
        ProjectRequest projectRequest = makeProjectRequest(po, user, notTime, extraData, description);
        this.save(projectRequest);

        return projectRequest.getId();
    }

    private ProjectRequest makeProjectRequest(ProjectCreatePO po, UserPOJO user, long notTime, Map<String, String> extraData, String description) {
        ProjectRequest projectRequest = new ProjectRequest();
        String contactEmail;
        String email = extraData.get("contactEmail");
        extraData.remove("description");
        if (!CheckUtils.isNull(email)){
            contactEmail=email;
            extraData.remove(email);
        }else{
            contactEmail= user.getAccount();
        }
        projectRequest.setCreatorId(user.getId());
        projectRequest.setName(po.getTitle());
        projectRequest.setDescription(description);
        projectRequest.setProposalDdl(po.getProposalDue());
        projectRequest.setSolutionDdl(po.getSolutionDue());
        projectRequest.setIsProcessingDone(false);
        projectRequest.setCreateTime(notTime);
        projectRequest.setCreatorId(user.getId());
        projectRequest.setLastModifiedTime(notTime);
        projectRequest.setExtraData(JSON.toJSONString(extraData));
        if (po.getIsDraft()){
            projectRequest.setStatus(ProjectRequestStatus.DRAFT);
        }else{
            projectRequest.setStatus(ProjectRequestStatus.OPEN_FOR_PROPOSAL);
        }
        projectRequest.setContactEmail(contactEmail);
        projectRequest.setProjectArea(po.getCategoryType());

        projectRequest.setCanProcess(false);
        return projectRequest;
    }

    /**
     * 获取某个project info
     * @param id    project id
     * @return
     */
    @Override
    public ProjectDetailInfoVO getProjectInfo(Integer id) {
        ProjectDetailInfoVO projectDetailInfoVO = new ProjectDetailInfoVO();
        ProjectRequest proj = this.getById(id);
        CheckUtils.assertNotNull(proj,"project request not exists");
        BeanUtils.copyProperties(proj,projectDetailInfoVO);
        Integer creatorId = proj.getCreatorId();
        projectDetailInfoVO.setCreatorName(userService.getById(creatorId).getFullName());

        return projectDetailInfoVO;
    }

    /**
     * 查此project是否可以被编辑
     * @param projectId
     * @return
     */
    @Override
    public Boolean canEditProject(Integer projectId) {
        ProjectRequest proj = this.getById(projectId);
        return TimeUtils.getTimeStamp() < proj.getProposalDdl();
    }

    /**
     * 修改project
     * @param po
     * @param http
     */
    @Override
    public void editProject(ProjectEditPO po,HttpServletRequest http) {
        UserPOJO user = UserUtils.getCurrentUser(http);
        ProjectRequest proj = this.getById(po.getProjectId());
        editProjectByParams(po, user, proj);
        proj.setLastModifiedTime(TimeUtils.getTimeStamp());

        this.updateById(proj);
    }

    private void editProjectByParams(ProjectEditPO po, UserPOJO user, ProjectRequest proj) {
        if (!proj.getCreatorId().equals(user.getId())){
            throw new CheckException("You cannot edit others project");
        }
        if (po.getTitle()!=null){
            proj.setName(po.getTitle());
        }
        if (po.getCategoryType()!=null){
            proj.setProjectArea(po.getCategoryType());
        }
        if (po.getProposalDue()!=null){
            proj.setProposalDdl(po.getProposalDue());
        }
        if (po.getSolutionDue()!=null){
            proj.setSolutionDdl(po.getSolutionDue());
        }
        Map<String, String> extraData = po.getExtraData();
        if (extraData !=null){
            String description = extraData.get("description");
            CheckUtils.assertNotNull(description,"description cannot be null");
            proj.setDescription(description);
            proj.setExtraData(JSON.toJSONString(extraData));
        }
        if (po.getContactEmail()!=null){
            proj.setContactEmail(po.getContactEmail());
        }
    }

    /**
     * 公司获取自己的project
     * @param status
     * @param isAscendingOrder  true->时间升序
     * @param searchKey
     * @param pageNo
     * @param pageSize
     * @param http
     * @return
     */
    @Override
    public IPage<ProjectInfoVO> listMyProjectRequests(String status, Boolean isAscendingOrder, String searchKey, Integer pageNo, Integer pageSize,HttpServletRequest http) {
        UserPOJO user = UserUtils.getCurrentUser(http);
        Page<ProjectInfoVO> pageSetting = new Page<>(pageNo, pageSize);
        if (isAscendingOrder){
            pageSetting.addOrder(OrderItem.asc("create_time"));
        }else {
            pageSetting.addOrder(OrderItem.desc("create_time"));
        }

        MPJLambdaWrapper<ProjectRequest> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .leftJoin(User.class,User::getId,ProjectRequest::getCreatorId)
                .eq(ProjectRequest::getCreatorId,user.getId());
        if (CheckUtils.isNotEmpty(status)){
            wrapper.eq(ProjectRequest::getStatus,status);
        }
//                //TODO like search key还没写
//                .like(ProjectRequest::getDescription,"%"+searchKey+"%")
//                .or()
//                .like(ProjectRequest::getName,"%"+searchKey+"%")
//                .or()
//                .like(User::getFullName,"%"+searchKey+"%")
        wrapper.selectAs(ProjectRequest::getName,"title")
                .select(ProjectRequest::getDescription)
                .selectAs(ProjectRequest::getCreatorId,"authorId")
                .select(ProjectRequest::getStatus)
                .select(ProjectRequest::getId)
                .selectAs(User::getFullName,"authorName");

        IPage<ProjectInfoVO> page = projectRequestMapper.selectJoinPage(pageSetting, ProjectInfoVO.class, wrapper);
        return page;
    }

    /**
     * 公司个人页
     * @param companyId
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public IPage<ProfileProjectInfoVO> listCompanyProfileProjectRequest(Integer companyId, Integer pageNo, Integer pageSize) {

        Page<ProfileProjectInfoVO> pageSetting = new Page<>(pageNo, pageSize);
        MPJLambdaWrapper<ProjectRequest> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .leftJoin(User.class,User::getId,ProjectRequest::getCreatorId)
                .eq(ProjectRequest::getCreatorId,companyId)
                .ne(Proposal::getStatus,ProjectRequestStatus.DRAFT)
                .select(ProjectRequest::getId)
                .selectAs(ProjectRequest::getName,"title")
                .select(ProjectRequest::getDescription)
                .select(ProjectRequest::getStatus)
                .select(ProjectRequest::getLastModifiedTime)
                .orderByAsc(ProjectRequest::getCreateTime);



        IPage<ProfileProjectInfoVO> page=projectRequestMapper.selectJoinPage(pageSetting,ProfileProjectInfoVO.class,wrapper);

        return page;
    }

    /**
     * 列出某个公司的project
     * @param creatorId
     * @param status
     * @param isAscendingOrder
     * @param searchKey
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public IPage<ProfileProjectInfoVO> listCompanyProjectRequests(Integer creatorId, String status, Boolean isAscendingOrder, String searchKey, Integer pageNo, Integer pageSize) {
        Page<ProfileProjectInfoVO> pageSetting = new Page<>(pageNo, pageSize);
        if (isAscendingOrder){
            pageSetting.addOrder(OrderItem.asc("create_time"));
        }else {
            pageSetting.addOrder(OrderItem.desc("create_time"));
        }
        if (status.equals(ProjectRequestStatus.DRAFT)){
            throw new CheckException("Cannot view draft project requests");
        }

        MPJLambdaWrapper<ProjectRequest> myWrapper = new MPJLambdaWrapper<>();
        myWrapper
                .leftJoin(User.class,User::getId,ProjectRequest::getCreatorId)
                .eq(ProjectRequest::getStatus,status)
                .eq(ProjectRequest::getCreatorId,creatorId)
                .select(ProjectRequest::getId)
                .selectAs(ProjectRequest::getName,"title")
                .select(ProjectRequest::getDescription)
                .select(ProjectRequest::getStatus)
                .select(ProjectRequest::getLastModifiedTime)
                .and(wrapper->wrapper.like(ProjectRequest::getDescription,"%"+searchKey+"%")
                        .or()
                        .like(ProjectRequest::getName,"%"+searchKey+"%")
                        .or()
                        .like(User::getFullName,"%"+searchKey+"%"));

        IPage<ProfileProjectInfoVO> page = projectRequestMapper.selectJoinPage(pageSetting, ProfileProjectInfoVO.class, myWrapper);
        return page;
    }

    /**
     * 学生看所有的project
     * @param status
     * @param isAscendingOrder
     * @param whatOrder
     * @param searchKey
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public IPage<ProjectInfoVO> listAllProjectRequests(String status, Boolean isAscendingOrder,String whatOrder, String searchKey, Integer pageNo, Integer pageSize){
        Page<ProjectInfoVO> pageSetting = new Page<>(pageNo, pageSize);
        if (status.equals(ProjectRequestStatus.DRAFT)){
            throw new CheckException("Cannot view draft project requests");
        }

        MPJLambdaWrapper<ProjectRequest> myWrapper = new MPJLambdaWrapper<>();
        myWrapper
                .leftJoin(User.class,User::getId,ProjectRequest::getCreatorId)
                .eq(ProjectRequest::getStatus,status)
                .select(ProjectRequest::getId)
                .selectAs(ProjectRequest::getName,"title")
                .select(ProjectRequest::getDescription)
                .selectAs(User::getId,"authorId")
                .selectAs(User::getFullName,"authorName")
                .select(ProjectRequest::getStatus)
                .select(ProjectRequest::getLastModifiedTime)
                .and(wrapper->wrapper.like(ProjectRequest::getDescription,"%"+searchKey+"%")
                        .or()
                        .like(ProjectRequest::getName,"%"+searchKey+"%")
                        .or()
                        .like(User::getFullName,"%"+searchKey+"%"));

        if(FilterName.PROPOSAL_DUE.equals(whatOrder)){
            if(isAscendingOrder){myWrapper.orderByAsc(ProjectRequest::getProposalDdl);}
            else{myWrapper.orderByDesc(ProjectRequest::getProposalDdl);}
        }else{
            if(isAscendingOrder){myWrapper.orderByAsc(ProjectRequest::getSolutionDdl);}
            else{myWrapper.orderByDesc(ProjectRequest::getSolutionDdl);}
        }

        IPage<ProjectInfoVO> page = projectRequestMapper.selectJoinPage(pageSetting, ProjectInfoVO.class, myWrapper);
        return page;
    }

    @Override
    public void deleteProject(Integer projectId, HttpServletRequest http) {
        ProjectRequest project = this.getById(projectId);
        UserPOJO currentUser = UserUtils.getCurrentUser(http);
        CheckUtils.assertNotNull(project,"project not exist");
        if (!ProjectRequest.isDraft(project)){
            throw  new CheckException("this project cannot delete");
        }
        if (!currentUser.getId().equals(project.getCreatorId())){
            throw new CheckException("you cannot delete others proposal");
        }
        this.removeById(project);

    }
}
