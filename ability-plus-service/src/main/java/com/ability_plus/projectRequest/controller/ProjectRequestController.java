package com.ability_plus.projectRequest.controller;


import com.ability_plus.projectRequest.entity.PO.ProjectCreatePO;
import com.ability_plus.projectRequest.entity.PO.ProjectEditPO;
import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.ability_plus.projectRequest.service.IProjectRequestService;
import com.ability_plus.utils.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public RestResponse createProjectRequest(@RequestBody ProjectCreatePO po){
        projectRequestService.createProjectRequest(po);
        return RestResponse.success();
    }

    @GetMapping("/getProjectInfo")
    @ApiOperation("get project request info")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "project id", required = true),
    })
    public RestResponse<ProjectInfoVO> getProjectInfo(@RequestParam Integer id){
        ProjectInfoVO projectInfo = projectRequestService.getProjectInfo(id);
        return RestResponse.success(projectInfo);
    }

    @GetMapping("/canEditProject")
    @ApiOperation("can this user can edit this project now")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "user id", required = true),
            @ApiImplicitParam(name = "projectId", value = "project id", required = true),
    })
    public RestResponse<Boolean> canEditProject(Integer userId,Integer projectId){
        return RestResponse.success(projectRequestService.canEditProject(userId,projectId));
    }

    @PostMapping("/editProject")
    @ApiOperation("edit project")
    public RestResponse editProject(@RequestBody ProjectEditPO po){
        projectRequestService.editProject(po);
        return RestResponse.success();
    }
}
