package com.ability_plus.forum.controller;


import com.ability_plus.forum.entity.PostIds;
import com.ability_plus.forum.entity.PostVO;
import com.ability_plus.forum.entity.Reply;
import com.ability_plus.forum.entity.ReplyVO;
import com.ability_plus.forum.service.IPostService;
import com.ability_plus.projectRequest.entity.PO.ProjectEditPO;
import com.ability_plus.utils.RestResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false),
    })
    public RestResponse<IPage<PostVO>> listAllPost(@RequestParam(value = "projectId") Integer projectId,
                                                   @RequestParam(value = "pageNo") Integer pageNo,
                                                   @RequestParam(value = "pageSize") Integer pageSize){
        return RestResponse.success(postService.listAllPost(projectId,pageNo,pageSize));
    }
    @GetMapping("/list_my_post")
    @ApiOperation("list_my_post")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false),
    })
    public RestResponse<IPage<PostVO>> listMyPost(@RequestParam(value = "pageNo") Integer pageNo,
                                                  @RequestParam(value = "pageSize") Integer pageSize,
                                                    HttpServletRequest http){
        return RestResponse.success(postService.listMyPost(http,pageNo,pageSize));
    }

    @PostMapping("/list_post_by_ids")
    @ApiOperation("list_my_post")
    public RestResponse<List<PostVO>> listPostByIds(@RequestBody List<Integer> ids){
        return RestResponse.success(postService.listPostByIds(ids));
    }

    @PostMapping("/delete_my_post")
    @ApiOperation("delete_my_post")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "postId", required = true),
    })
    public RestResponse deleteMyPost(@RequestParam(value = "postId") Integer postId,
                                                  HttpServletRequest http){
        postService.deleteMyPost(postId,http);
        return RestResponse.success();
    }
    @PostMapping("/edit_my_post")
    @ApiOperation("edit_my_post")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "postId", required = true),
            @ApiImplicitParam(name = "data", value = "data", required = true),
            @ApiImplicitParam(name = "pin", value = "pin", required = false),
    })
    public RestResponse editMyPost(@RequestParam(value = "postId") Integer postId,
                                   @RequestParam(value = "data") String  data,
                                   @RequestParam(value = "pin",required = false) Boolean  isPin,
                                                  HttpServletRequest http){
        postService.editMyPost(postId,data,isPin,http);
        return RestResponse.success();
    }

    @GetMapping("/new_reply_post")
    @ApiOperation("my_new_reply_post")

    public RestResponse<List<Integer>> newReplyPost(HttpServletRequest http){

        return RestResponse.success(postService.newReplyPost(http));
    }

    @GetMapping("/get_a_post_reply_info")
    @ApiOperation("get_a_post_info")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "postId", required = true),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = false),
    })

    public RestResponse<IPage<ReplyVO>> getAPostReplyInfo(@RequestParam(value = "postId") Integer postId,
                                                          @RequestParam(value = "pageNo") Integer pageNo,
                                                          @RequestParam(value = "pageSize") Integer pageSize
                                                   ){

        return RestResponse.success(postService.getAPostInfo(postId,pageNo,pageSize));
    }


}
