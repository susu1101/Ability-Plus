package com.ability_plus.projectRequest.service;


import com.ability_plus.projectRequest.entity.PO.ProjectCreatePO;
import com.ability_plus.projectRequest.entity.PO.ProjectEditPO;
import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.VO.ProjectDetailInfoVO;
import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.base.MPJBaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
public interface IProjectRequestService extends IService<ProjectRequest>, MPJBaseService<ProjectRequest> {
    /**
     * create project request
     * @param po
     * @param http
     * @return
     */
    public Integer createProjectRequest(ProjectCreatePO po, HttpServletRequest http);

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
    IPage<ProjectInfoVO> listMyProjectRequests(String status, Boolean isAscendingOrder, String searchKey, Integer pageNo, Integer pageSize, HttpServletRequest http);

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
