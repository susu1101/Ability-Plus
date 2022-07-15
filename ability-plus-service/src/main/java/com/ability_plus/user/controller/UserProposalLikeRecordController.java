package com.ability_plus.user.controller;


import com.ability_plus.user.service.IUserProposalLikeRecordService;
import com.ability_plus.utils.RestResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/user_proposal_like_record")
public class UserProposalLikeRecordController {
    @Autowired
    IUserProposalLikeRecordService userProposalLikeRecordService;
    @PostMapping("/like")
    @ApiOperation("like")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proposalId", value = "proposalId", required = true),
    })
    public RestResponse like(@RequestParam Integer proposalId, HttpServletRequest http){
        userProposalLikeRecordService.like(proposalId,http);
        return RestResponse.success();
    }
    @PostMapping("/unlike")
    @ApiOperation("unlike")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proposalId", value = "proposalId", required = true),
    })
    public RestResponse unlike(@RequestParam Integer proposalId, HttpServletRequest http){
        userProposalLikeRecordService.unlike(proposalId,http);
        return RestResponse.success();
    }
    @PostMapping("/get_like_num")
    @ApiOperation("get_like_num")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proposalId", value = "proposalId", required = true),
    })
    public RestResponse<Integer> getLikeNum(@RequestParam Integer proposalId){
        return RestResponse.success(userProposalLikeRecordService.getLikeNum(proposalId));
    }

}
