package com.ability_plus.proposal.controller;


import com.ability_plus.proposal.entity.PO.ProposalPO;
import com.ability_plus.proposal.service.IProposalService;
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
@RequestMapping("/proposal")
@Api(value="proposal")
public class ProposalController {
    @Autowired
    IProposalService proposalService;

    @PostMapping("createProposal")
    @ApiOperation("create proposal")
    public RestResponse<Integer> createProposal(@RequestBody ProposalPO po){
        proposalService.createProposal(po);
        return RestResponse.success();
    }

    @GetMapping("/canEditProposal")
    @ApiOperation("can this user can edit this proposal now")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "user id", required = true),
            @ApiImplicitParam(name = "proposalId", value = "project id", required = true),
    })
    public RestResponse<Boolean> canEditProposal(Integer userId,Integer proposalId){
        return RestResponse.success(proposalService.canEditProposal(userId,proposalId));
    }

    @PostMapping("/editProposal")
    @ApiOperation("edit proposal")
    public RestResponse editProposal(@RequestBody ProposalPO po){
        proposalService.editProposal(po);
        return RestResponse.success();
    }

}
