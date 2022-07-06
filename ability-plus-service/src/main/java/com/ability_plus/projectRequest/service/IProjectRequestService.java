package com.ability_plus.projectRequest.service;


import com.ability_plus.projectRequest.entity.PO.ProjectCreatePO;
import com.ability_plus.projectRequest.entity.PO.ProjectEditPO;
import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.VO.ProjectDetailInfoVO;
import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
public interface IProjectRequestService extends IService<ProjectRequest> {
    /**
     * create project request
     * @param po data
     * @return project id
     */
    public Integer createProjectRequest(ProjectCreatePO po);

    /**
     * send project information to font-end
     * @param id    project id
     * @return      VO
     */
    public ProjectDetailInfoVO getProjectInfo(Integer id);

    /**
     * does this user now can edit this project
     * @param projectId
     * @return
     */
    public Boolean canEditProject(Integer projectId);

    /**
     * edit a project
     * @param po
     */
    public void editProject(ProjectEditPO po);

    /**
     * list all project request by condition
     * @param status
     * @param isAscendingOrder
     * @param searchKey
     * @return
     */
    List<ProjectInfoVO> listProjectRequests(String status, Boolean isAscendingOrder, String searchKey, Integer pageNo, Integer pageSize);

    /**
     * list all project request created by a company
     * @param creatorId
     * @param status
     * @param isAscendingOrderTime
     * @param searchKey
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<ProjectInfoVO> listCompanyProjectRequests(Integer creatorId, String status, Boolean isAscendingOrderTime, String searchKey, Integer pageNo, Integer pageSize);
}
