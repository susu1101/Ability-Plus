package com.ability_plus.projectRequest.service.impl;


import com.ability_plus.projectRequest.entity.PO.ProjectCreatePO;
import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.ability_plus.projectRequest.mapper.ProjectRequestMapper;
import com.ability_plus.projectRequest.service.IProjectRequestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
    public void createProjectRequest(ProjectCreatePO po) {
        return;
    }

    @Override
    public ProjectInfoVO getProjectInfo(Integer id) {
        return null;
    }

//    @Override
//    public void createProjectRequest(String title, Integer categoryType, Integer proposalDue, Integer solutionDue, String extraData,Boolean isSubmission) {
//        return;
//    }
}
