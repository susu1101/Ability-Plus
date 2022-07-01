package com.ability_plus.proposal.controller;


import com.ability_plus.projectRequest.entity.PO.ProjectEditPO;
import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.ability_plus.proposal.entity.PO.ProposalCreatePO;
import com.ability_plus.proposal.entity.PO.ProposalEditPO;
import com.ability_plus.proposal.entity.VO.ProposalInfoVO;
import com.ability_plus.proposal.service.IProposalService;
import com.ability_plus.utils.RestResponse;
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
@RequestMapping("/proposal")
@Api(value="proposal")
public class ProposalController {
    @Autowired
    IProposalService proposalService;

    @PostMapping("create_proposal")
    @ApiOperation("create proposal")
    public RestResponse<Integer> createProposal(@RequestBody ProposalCreatePO po){
        proposalService.createProposal(po);
        return RestResponse.success();
    }

    @GetMapping("/can_edit_proposal")
    @ApiOperation("can this user can edit this proposal now")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "user id", required = true),
            @ApiImplicitParam(name = "proposalId", value = "project id", required = true),
    })
    public RestResponse<Boolean> canEditProposal(Integer proposalId){
        return RestResponse.success(proposalService.canEditProposal(proposalId));
    }

    @PostMapping("/edit_proposal")
    @ApiOperation("edit proposal")
    public RestResponse editProposal(@RequestBody ProposalEditPO po){
        proposalService.editProposal(po);
        return RestResponse.success();
    }

//    @ApiOperation("list proposal request by condition")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "status", value = "status of project", required = true),
//            @ApiImplicitParam(name = "isAscendingOrder", value = "is the submission order by ascending", required = true),
//            @ApiImplicitParam(name = "searchKey", value = "the search key", required = true),
//            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
//            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),
//
//    })
//    @GetMapping("/list_proposal_request")
//    public RestResponse<List<ProposalInfoVO>> listProposalRequests(String status, Boolean isAscendingOrder, String searchKey, Integer pageNo, Integer pageSize){
//        List<ProposalInfoVO> proposalInfoVOS = proposalService.listProposalRequests(status, isAscendingOrder, searchKey,pageNo,pageSize);
//        return RestResponse.success(proposalInfoVOS);
//    }
}
