package com.ability_plus.projectRequest.service.impl;


import com.ability_plus.projectRequest.entity.PO.ProjectCreatePO;
import com.ability_plus.projectRequest.entity.PO.ProjectEditPO;
import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.VO.ProjectDetailInfoVO;
import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.ability_plus.projectRequest.mapper.ProjectRequestMapper;
import com.ability_plus.projectRequest.service.IProjectRequestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class ProjectRequestServiceImpl extends ServiceImpl<ProjectRequestMapper, ProjectRequest> implements IProjectRequestService {
    @Override
    public Integer createProjectRequest(ProjectCreatePO po) {
        return 0;
    }

    @Override
    public ProjectDetailInfoVO getProjectInfo(Integer id) {
        return new ProjectDetailInfoVO();
    }

    @Override
    public Boolean canEditProject(Integer projectId) {
        return null;
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
}
