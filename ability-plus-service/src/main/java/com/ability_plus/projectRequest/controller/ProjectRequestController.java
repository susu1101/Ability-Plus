package com.ability_plus.projectRequest.controller;


import com.ability_plus.projectRequest.entity.PO.ProjectCreatePO;
import com.ability_plus.projectRequest.entity.PO.ProjectEditPO;
import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.ability_plus.projectRequest.service.IProjectRequestService;
import com.ability_plus.utils.RestResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public RestResponse<Integer> createProjectRequest(@RequestBody ProjectCreatePO po){
        projectRequestService.createProjectRequest(po);
        return RestResponse.success();
    }

    @GetMapping("/get_project_info")
    @ApiOperation("get project request info")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "project id", required = true),
    })
    public RestResponse<ProjectInfoVO> getProjectInfo(@RequestParam Integer id){
        ProjectInfoVO projectInfo = projectRequestService.getProjectInfo(id);
        return RestResponse.success(projectInfo);
    }

    @GetMapping("/can_edit_project")
    @ApiOperation("can this user can edit this project now")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "user id", required = true),
            @ApiImplicitParam(name = "projectId", value = "project id", required = true),
    })
    public RestResponse<Boolean> canEditProject(Integer projectId){
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
            @ApiImplicitParam(name = "searchKey", value = "the search key", required = true),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),

    })
    @GetMapping("/list_project_request")
    public RestResponse<List<ProjectInfoVO>> listProjectRequests(String status, Boolean isAscendingOrder, String searchKey,Integer pageNo,Integer pageSize){
        List<ProjectInfoVO> projectInfoVOS = projectRequestService.listProjectRequests(status, isAscendingOrder, searchKey,pageNo,pageSize);
        return RestResponse.success(projectInfoVOS);
    }



}
