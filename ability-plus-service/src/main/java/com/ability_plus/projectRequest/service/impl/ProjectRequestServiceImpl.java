package com.ability_plus.projectRequest.service.impl;


import com.ability_plus.projectRequest.entity.PO.ProjectCreatePO;
import com.ability_plus.projectRequest.entity.PO.ProjectEditPO;
import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.VO.ProjectDetailInfoVO;
import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.ability_plus.projectRequest.mapper.ProjectRequestMapper;
import com.ability_plus.projectRequest.service.IProjectRequestService;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.User;
import com.ability_plus.user.entity.UserPOJO;
import com.ability_plus.user.service.IUserService;
import com.ability_plus.utils.CheckUtils;
import com.ability_plus.utils.TimeUtils;
import com.ability_plus.utils.UserUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class ProjectRequestServiceImpl extends ServiceImpl<ProjectRequestMapper, ProjectRequest> implements IProjectRequestService {

    @Autowired
    IUserService userService;
    @Override
    public Integer createProjectRequest(ProjectCreatePO po, HttpServletRequest http) {
        //check input
        CheckUtils.assertNotNull(po,"input cannot be null");
        UserPOJO user = UserUtils.getCurrentUser(http);
        if (!user.getIsCompany()){
            throw new CheckException("Students do not have permission to post projects");
        }
        int notTime = TimeUtils.getTimeStamp();
        Map<String, String> extraData = po.getExtraData();
        String description = extraData.get("description");
        //check description
        CheckUtils.assertNotNull(description,"Description cannot be null");
        ProjectRequest projectRequest = makeProjectRequest(po, user, notTime, extraData, description);
        this.save(projectRequest);
        return projectRequest.getId();
    }

    private ProjectRequest makeProjectRequest(ProjectCreatePO po, UserPOJO user, int notTime, Map<String, String> extraData, String description) {
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
        projectRequest.setIsDraft(po.getIsDraft());
        projectRequest.setContactEmail(contactEmail);
        projectRequest.setProjectArea(po.getCategoryType());

        projectRequest.setCanProcess(false);
        return projectRequest;
    }

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

    @Override
    public Boolean canEditProject(Integer projectId) {
        ProjectRequest proj = this.getById(projectId);
        return TimeUtils.getTimeStamp() > proj.getProposalDdl();
    }

    @Override
    public void editProject(ProjectEditPO po) {

    }

    @Override
    public List<ProjectInfoVO> listProjectRequests(String status, Boolean isAscendingOrder, String searchKey, Integer pageNo, Integer pageSize) {
        return null;
    }


//    @Override
//    public void createProjectRequest(String title, Integer categoryType, Integer proposalDue, Integer solutionDue, String extraData,Boolean isSubmission) {
//        return;
//    }

    @Override
    public List<ProjectInfoVO> listCompanyProjectRequests(Integer creatorId, String status, Boolean isAscendingOrderTime, String searchKey, Integer pageNo, Integer pageSize) {
        return null;
    }
}
