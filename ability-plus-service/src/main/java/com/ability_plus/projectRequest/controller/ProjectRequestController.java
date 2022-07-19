package com.ability_plus.projectRequest.controller;


import com.ability_plus.projectRequest.entity.PO.ProjectCreatePO;
import com.ability_plus.projectRequest.entity.PO.ProjectEditPO;
import com.ability_plus.projectRequest.entity.VO.ProjectDetailInfoVO;
import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.ability_plus.projectRequest.entity.VO.ProfileProjectInfoVO;
import com.ability_plus.projectRequest.service.IProjectRequestService;
import com.ability_plus.utils.RestResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public RestResponse editProject(@RequestBody ProjectEditPO po,HttpServletRequest http){
        projectRequestService.editProject(po,http);
        return RestResponse.success();
    }

    @ApiOperation("list my project request by condition")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "status of project", required = false),
            @ApiImplicitParam(name = "isAscendingOrder", value = "is the submission order by ascending", required = true),
            @ApiImplicitParam(name = "searchKey", value = "the search key", required = false),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),

    })
    @GetMapping("/list_my_project_request")
    public RestResponse<IPage<ProjectInfoVO>> listMyProjectRequests(@RequestParam(value = "status",required = false) String status,
                                                                   @RequestParam(value = "isAscendingOrder") Boolean isAscendingOrder,
                                                                   @RequestParam(value = "searchKey",required = false) String searchKey,
                                                                   @RequestParam(value = "pageNo") Integer pageNo,
                                                                   @RequestParam(value = "pageSize") Integer pageSize,
                                                                   HttpServletRequest http){
        IPage<ProjectInfoVO> projectInfoVO = projectRequestService.listMyProjectRequests(status, isAscendingOrder, searchKey,pageNo,pageSize,http);
        return RestResponse.success(projectInfoVO);
    }

    @GetMapping("/list_company_profile_project_request")
    @ApiOperation("list company profile project request")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyId", value = "company Id", required = true),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true)

    })
    public RestResponse<IPage<ProfileProjectInfoVO>> listCompanyProfileProjectRequest(@RequestParam(value = "companyId") Integer companyId,
                                                                               @RequestParam(value = "pageNo") Integer pageNo,
                                                                               @RequestParam(value = "pageSize") Integer pageSize){
        IPage<ProfileProjectInfoVO> profileProjectInfoVO=projectRequestService.listCompanyProfileProjectRequest(companyId,pageNo,pageSize);
        return RestResponse.success(profileProjectInfoVO);
    }



    @ApiOperation("list all project request created by a company")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "creatorId", value = "id of proposal creator", required = true),
            @ApiImplicitParam(name = "status", value = "required status to filter", required = true),
            @ApiImplicitParam(name = "isAscendingOrder", value = "required order to sort", required = true),
            @ApiImplicitParam(name = "searchKey", value = "the search key", required = true),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),
    })
    @GetMapping("/list_company_project_request")
    public RestResponse<IPage<ProfileProjectInfoVO>> listCompanyProjectRequests(@RequestParam(value="creatorId") Integer creatorId,
                                                                 @RequestParam(value = "status") String status,
                                                                 @RequestParam(value = "isAscendingOrderTime") Boolean isAscendingOrder,
                                                                 @RequestParam(value = "searchKey",required = false) String searchKey,
                                                                 @RequestParam(value = "pageNo") Integer pageNo,
                                                                 @RequestParam(value = "pageSize") Integer pageSize){
        IPage<ProfileProjectInfoVO> profileProjectInfoVO = projectRequestService.listCompanyProjectRequests(creatorId, status, isAscendingOrder, searchKey, pageNo, pageSize);
        return RestResponse.success(profileProjectInfoVO);
    }


    @GetMapping("/list_all_project_requests")
    @ApiOperation("list all project requests")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "status of project"),
            @ApiImplicitParam(name = "isAscendingOrder", value = "is the submission order by ascending", required = true),
            @ApiImplicitParam(name = "whatOrder",value = "sort by what order", required = true),
            @ApiImplicitParam(name = "searchKey", value = "the search key", required = false),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),

    })
    public RestResponse<IPage<ProjectInfoVO>> listAllProjectRequests(@RequestParam(value = "status",required = false) String status,
                                                                    @RequestParam(value = "isAscendingOrder") Boolean isAscendingOrder,
                                                                    @RequestParam(value = "whatOrder") String whatOrder,
                                                                    @RequestParam(value = "searchKey",required = false) String searchKey,
                                                                    @RequestParam(value = "pageNo") Integer pageNo,
                                                                    @RequestParam(value = "pageSize") Integer pageSize){
        IPage<ProjectInfoVO> projectInfoVO=projectRequestService.listAllProjectRequests(status,isAscendingOrder,whatOrder,searchKey,pageNo,pageSize);
        return RestResponse.success(projectInfoVO);
    }

    @ApiOperation("delete draft project")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "projectId", required = true),
    })
    @PostMapping("/delete_project")
    public RestResponse deleteProject(@RequestParam Integer projectId,
                                       HttpServletRequest http){
        projectRequestService.deleteProject(projectId,http);
        return RestResponse.success();
    }



}
