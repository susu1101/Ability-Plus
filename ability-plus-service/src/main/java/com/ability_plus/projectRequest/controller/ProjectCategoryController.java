package com.ability_plus.projectRequest.controller;


import com.ability_plus.projectRequest.entity.ProjectCategory;
import com.ability_plus.projectRequest.service.IProjectCategoryService;
import com.ability_plus.utils.RestResponse;
import io.swagger.annotations.Api;
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
 * @since 2022-07-01
 */
@RestController
@RequestMapping("/project-category")
@Api(value="listAllCategory")
public class ProjectCategoryController {
    @Autowired
    IProjectCategoryService projectCategoryService;
    @GetMapping("/create_project_request")
    @ApiOperation("get all category of area")
    public RestResponse<List<ProjectCategory>> createProjectRequest(){
        List<ProjectCategory> categories = projectCategoryService.list();
        return RestResponse.success(categories);
    }
}
