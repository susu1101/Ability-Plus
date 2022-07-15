package com.ability_plus.user.controller;



import com.ability_plus.utils.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ability_plus.user.service.IUserProposalLikeRecordService;
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
@Api(value="user_proposal_like_record")
public class UserProposalLikeRecordController {
    @Autowired
    IUserProposalLikeRecordService userProposalLikeRecordService;


    @PostMapping("/like_proposal")
    @ApiOperation("like_proposal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proposalId", value = "id of the proposal", required = true),
    })
    public RestResponse<Integer> likeRecord(@RequestParam Integer proposalId, HttpServletRequest http){

        return RestResponse.success(userProposalLikeRecordService.likeRecord(proposalId, http));
    }



    @PostMapping("/unlike_proposal")
    @ApiOperation("unlike_proposal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proposalId", value = "id of the proposal", required = true),
    })
    public RestResponse cancelLikeRecord(@RequestParam Integer proposalId, HttpServletRequest http){
        userProposalLikeRecordService.cancelLikeRecord(proposalId, http);
        return RestResponse.success();
    }
}
