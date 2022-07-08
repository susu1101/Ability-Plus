package com.ability_plus.projectRequest.controller;


import com.ability_plus.projectRequest.entity.PO.ProjectCreatePO;
import com.ability_plus.projectRequest.entity.PO.ProjectEditPO;
import com.ability_plus.projectRequest.entity.VO.ProjectDetailInfoVO;
import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.ability_plus.projectRequest.service.IProjectRequestService;
import com.ability_plus.utils.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
@RestController

@Api(value="project_request")
@RequestMapping("/project")
public class ProjectRequestController {
    @Autowired
    IProjectRequestService projectRequestService;

    @PostMapping("/create_project_request")
    @ApiOperation("create project request")
    public RestResponse<Integer> createProjectRequest(@RequestBody ProjectCreatePO po, HttpServletRequest http){
        return RestResponse.success(projectRequestService.createProjectRequest(po,http));
    }

    @GetMapping("/get_project_info")
    @ApiOperation("get project request info")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "project id", required = true),
    })
    public RestResponse<ProjectDetailInfoVO> getProjectInfo(@RequestParam Integer id){
        ProjectDetailInfoVO projectInfo = projectRequestService.getProjectInfo(id);
        return RestResponse.success(projectInfo);
    }

    @GetMapping("/can_edit_project")
    @ApiOperation("can this user can edit this project now")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "user id", required = true),
            @ApiImplicitParam(name = "projectId", value = "project id", required = true),
    })
    public RestResponse<Boolean> canEditProject(@RequestParam(value = "projectId") Integer projectId){
        return RestResponse.success(projectRequestService.canEditProject(projectId));
    }

    @PostMapping("/edit_project")
    @ApiOperation("edit project")
    public RestResponse editProject(@RequestBody ProjectEditPO po){
        projectRequestService.editProject(po);
        return RestResponse.success();
    }

    @ApiOperation("list project request by condition")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "status of project", required = true),
            @ApiImplicitParam(name = "isAscendingOrder", value = "is the submission order by ascending", required = true),
            @ApiImplicitParam(name = "searchKey", value = "the search key", required = false),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),

    })
    @GetMapping("/list_project_request")
    public RestResponse<List<ProjectInfoVO>> listProjectRequests(@RequestParam(value = "status") String status,
                                                                 @RequestParam(value = "isAscendingOrder") Boolean isAscendingOrder,
                                                                 @RequestParam(value = "searchKey",required = false) String searchKey,
                                                                 @RequestParam(value = "pageNo") Integer pageNo,
                                                                 @RequestParam(value = "pageSize") Integer pageSize){
        List<ProjectInfoVO> projectInfoVO = projectRequestService.listProjectRequests(status, isAscendingOrder, searchKey,pageNo,pageSize);
        return RestResponse.success(projectInfoVO);
    }
    @ApiOperation("list all project request created by a company")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "creatorId", value = "id of proposal creator", required = true),
            @ApiImplicitParam(name = "status", value = "required status to filter", required = true),
            @ApiImplicitParam(name = "isAscendingOrderTime", value = "required order to sort", required = true),
            @ApiImplicitParam(name = "searchKey", value = "the search key", required = true),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),
    })
    @GetMapping("/list_company_project_request")
    public RestResponse<List<ProjectInfoVO>> listCompanyProjectRequests(@RequestParam(value="creatorId") Integer creatorId,
                                                                 @RequestParam(value = "status") String status,
                                                                 @RequestParam(value = "isAscendingOrderTime") Boolean isAscendingOrderTime,
                                                                 @RequestParam(value = "searchKey",required = false) String searchKey,
                                                                 @RequestParam(value = "pageNo") Integer pageNo,
                                                                 @RequestParam(value = "pageSize") Integer pageSize){
        List<ProjectInfoVO> projectInfoVO = projectRequestService.listCompanyProjectRequests(creatorId, status, isAscendingOrderTime, searchKey, pageNo, pageSize);
        return RestResponse.success(projectInfoVO);
    }



}
