package com.ability_plus.proposal.controller;


import com.ability_plus.proposal.entity.PO.ProposalCreatePO;
import com.ability_plus.proposal.entity.PO.ProposalEditPO;
import com.ability_plus.proposal.entity.Proposal;
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
    public RestResponse<Boolean> canEditProposal(@RequestParam(value = "proposalId") Integer proposalId){
        return RestResponse.success(proposalService.canEditProposal(proposalId));
    }

    @PostMapping("/edit_proposal")
    @ApiOperation("edit proposal")
    public RestResponse editProposal(@RequestBody ProposalEditPO po){
        proposalService.editProposal(po);
        return RestResponse.success();
    }

    @ApiOperation("list proposal request by condition")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ranking", value = "rank of proposal", required = true),
            @ApiImplicitParam(name = "isAscendingOrderTime", value = "is the submission time order by ascending", required = true),
            @ApiImplicitParam(name = "searchKey", value = "the search key", required = true),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),

    })
    @GetMapping("/list_proposal_request")
    public RestResponse<List<ProposalInfoVO>> listProposalRequests(@RequestParam(value = "ranking") String ranking,
                                                                   @RequestParam(value = "isAscendingOrderTime") Boolean isAscendingOrderTime,
                                                                   @RequestParam(value = "searchKey",required = false) String searchKey,
                                                                   @RequestParam(value = "pageNo") Integer pageNo,
                                                                   @RequestParam(value = "pageSize") Integer pageSize){
        List<ProposalInfoVO> proposalInfoVOS = proposalService.listProposalRequests(ranking, isAscendingOrderTime, searchKey,pageNo,pageSize);
        return RestResponse.success(proposalInfoVOS);
    }

    @ApiOperation("select to approve proposal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proposalIds", value = "ids of proposal", required = true)
    })
    @PostMapping("/select_proposal")
    public RestResponse selectProposal(@RequestParam(value="proposalIds") List<Integer> ids){
        return RestResponse.success(proposalService.selectProposal(ids));
    }

    @ApiOperation("get proposal detail infomation")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proposalId", value = "id of proposal", required = true)
    })
    @GetMapping("/get_proposal_detail_info")
    public RestResponse<Proposal> getProposalInfo(@RequestParam(value="proposalId") Integer proposalId){
        return RestResponse.success(proposalService.getProposalInfo(proposalId));
    }

    @ApiOperation("get proposals created by a student")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "required status to filter", required = true),
            @ApiImplicitParam(name = "isAscendingOrderTime", value = "required order to sort", required = true),
            @ApiImplicitParam(name = "searchKey", value = "the search key", required = true),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),
    })
    @GetMapping("/list_student_proposal_request")
    public RestResponse<List<ProposalInfoVO>> listStudentProposalRequest(@RequestParam(value = "status") String status,
                                                                  @RequestParam(value = "isAscendingOrderTime") Boolean isAscendingOrderTime,
                                                                  @RequestParam(value = "searchKey",required = false) String searchKey,
                                                                  @RequestParam(value = "pageNo") Integer pageNo,
                                                                  @RequestParam(value = "pageSize") Integer pageSize){
        List<ProposalInfoVO> proposalInfoVOS = proposalService.listStudentProposalRequests(status, isAscendingOrderTime, searchKey,pageNo,pageSize);
        return RestResponse.success(proposalInfoVOS);
    }

    @ApiOperation("get outstanding proposals request")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isAscendingOrderLike", value = "is the number of likes order by ascending", required = true),
            @ApiImplicitParam(name = "isAscendingOrderTime", value = "is the submission time order by ascending", required = true),
            @ApiImplicitParam(name = "searchKey", value = "the search key", required = true),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),
    })
    @GetMapping("/list_outstanding_proposal_request")
    public RestResponse<List<ProposalInfoVO>> listOutstandingProposalsByUser(@RequestParam(value="isAscendingOrderLike") Boolean isAscendingOrderLike,
                                                                  @RequestParam(value = "isAscendingOrderTime") Boolean isAscendingOrderTime,
                                                                  @RequestParam(value = "searchKey",required = false) String searchKey,
                                                                  @RequestParam(value = "pageNo") Integer pageNo,
                                                                  @RequestParam(value = "pageSize") Integer pageSize){
        List<ProposalInfoVO> proposalInfoVOS = proposalService.listOutstandingProposalRequest(isAscendingOrderLike, isAscendingOrderTime, searchKey,pageNo,pageSize);
        return RestResponse.success(proposalInfoVOS);
    }

}
