package com.ability_plus.forum.controller;


import com.ability_plus.forum.entity.PostVO;
import com.ability_plus.forum.service.IPostService;
import com.ability_plus.projectRequest.entity.PO.ProjectEditPO;
import com.ability_plus.utils.RestResponse;
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
 * @author sjx
 * @since 2022-07-24
 */
@RestController
@RequestMapping("/forum/post")
public class PostController {
    @Autowired
    IPostService postService;

    @PostMapping("/new_post")
    @ApiOperation("new a post")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "data", required = true),
            @ApiImplicitParam(name = "projectId", value = "projectId", required = true),
            @ApiImplicitParam(name="isPin",value="isPin")
    })
    public RestResponse newPost(@RequestParam(value = "data") String data,
                                @RequestParam(value = "projectId") Integer projectId,
                                @RequestParam(value = "isPin",required = false) Boolean isPin,
                                HttpServletRequest http){
        postService.newPost(data,projectId,isPin,http);
        return RestResponse.success();
    }
    @GetMapping("/list_all_post")
    @ApiOperation("list_all_post")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "projectId", required = true),
    })
    public RestResponse<List<PostVO>> listAllPost(@RequestParam(value = "projectId") Integer projectId){
        return RestResponse.success(postService.listAllPost(projectId));
    }
    @GetMapping("/list_my_post")
    @ApiOperation("list_my_post")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "projectId", required = true),
    })
    public RestResponse<List<PostVO>> listAllPost(@RequestParam(value = "projectId") Integer projectId,
                                    HttpServletRequest http){
        return RestResponse.success(postService.listMyPost(projectId,http));
    }



}
