package com.ability_plus.proposal.controller;


import com.ability_plus.projectRequest.entity.VO.ProjectInfoVO;
import com.ability_plus.proposal.entity.PO.ProposalBatchProcessRequest;
import com.ability_plus.proposal.entity.PO.ProposalCreatePO;
import com.ability_plus.proposal.entity.PO.ProposalEditPO;
import com.ability_plus.proposal.entity.Proposal;
import com.ability_plus.proposal.entity.ProposalIds;
import com.ability_plus.proposal.entity.VO.*;
import com.ability_plus.proposal.service.IProposalService;
import com.ability_plus.utils.RestResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.ResultSet;
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
    public RestResponse<Integer> createProposal(@RequestBody ProposalCreatePO po, HttpServletRequest http){
        proposalService.createProposal(po,http);
        return RestResponse.success();
    }

    @GetMapping("/can_edit_proposal")
    @ApiOperation("can this user can edit this proposal now")
    @ApiImplicitParams({
//            @ApiImplicitParam(name = "userId", value = "user id", required = true),
            @ApiImplicitParam(name = "proposalId", value = "project id", required = true),
    })
    public RestResponse<Boolean> canEditProposal(@RequestParam(value = "proposalId") Integer proposalId,HttpServletRequest http){
        return RestResponse.success(proposalService.canEditProposal(proposalId,http));
    }

    @PostMapping("/edit_proposal")
    @ApiOperation("edit proposal")
    public RestResponse editProposal(@RequestBody ProposalEditPO po,HttpServletRequest http){
        proposalService.editProposal(po,http);
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



    @ApiOperation("get proposal detail infomation")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proposalId", value = "id of proposal", required = true)
    })
    @GetMapping("/get_proposal_detail_info")
    public RestResponse<ProposalDetailVO> getProposalInfo(@RequestParam(value="proposalId") Integer proposalId){
        return RestResponse.success(proposalService.getProposalInfo(proposalId));
    }

    @ApiOperation("get my proposals")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "required status to filter", required = true),
            @ApiImplicitParam(name = "isAscendingOrder", value = "required order to sort", required = true),
            @ApiImplicitParam(name = "whatOrder", value = "which order to sort", required = true),
            @ApiImplicitParam(name = "searchKey", value = "the search key", required = true),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),
    })
    @GetMapping("/list_my_proposal")
    public RestResponse<IPage<StudentMyProposalVO>> listMyProposal(@RequestParam(value = "status") String status,
                                                                   @RequestParam(value = "isAscendingOrder") Boolean isAscendingOrder,
                                                                   @RequestParam(value = "whatOrder") String whatOrder,
                                                                   @RequestParam(value = "searchKey",required = false) String searchKey,
                                                                   @RequestParam(value = "pageNo") Integer pageNo,
                                                                   @RequestParam(value = "pageSize") Integer pageSize,
                                                                   HttpServletRequest http){
        IPage<StudentMyProposalVO> proposalInfoVO = proposalService.listMyProposal(status,isAscendingOrder,whatOrder,searchKey,pageNo,pageSize,http);
        return RestResponse.success(proposalInfoVO);
    }


    /*list student profile proposal*/
    @GetMapping("/list_student_profile_proposals")
    @ApiOperation("list student profile proposals")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "creatorId", value = "id of proposal creator", required = true),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),
    })
    public RestResponse<IPage<ProposalInfoVO>> listStudentProfileProposals(@RequestParam(value="creatorId") Integer creatorId,
                                                                           @RequestParam(value = "pageNo") Integer pageNo,
                                                                           @RequestParam(value = "pageSize") Integer pageSize){
        IPage<ProposalInfoVO> proposalInfoVO= proposalService.listStudentProfileProposals(creatorId,pageNo,pageSize);
        return RestResponse.success(proposalInfoVO);
    }


    @GetMapping("/list_student_proposal")
    @ApiOperation("list student proposal")
    @ApiImplicitParams({
            @ApiImplicitParam(name="creatorId",value="id of proposal creator",required = true),
            @ApiImplicitParam(name = "isAscendingOrderLastModifiedTime", value = "required order to sort", required = true),
            @ApiImplicitParam(name = "searchKey", value = "the search key", required = true),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),
    })
    public RestResponse<IPage<ProposalInfoVO>> listStudentProposal(@RequestParam(value="creatorId") Integer creatorId,
                                                                   @RequestParam(value = "isAscendingOrderLastModifiedTime") Boolean isAscendingOrderLastModifiedTime,
                                                                   @RequestParam(value = "searchKey",required = false) String searchKey,
                                                                   @RequestParam(value = "pageNo") Integer pageNo,
                                                                   @RequestParam(value = "pageSize") Integer pageSize){
        IPage<ProposalInfoVO> proposalInfoVO = proposalService.listStudentProposal(creatorId, isAscendingOrderLastModifiedTime, searchKey,pageNo,pageSize);
        return RestResponse.success(proposalInfoVO);
    }



    @ApiOperation("get outstanding proposals request")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isAscendingOrderLike", value = "is the number of likes order by ascending", required = true),
            @ApiImplicitParam(name = "isAscendingOrderTime", value = "is the submission time order by ascending", required = true),
            @ApiImplicitParam(name = "searchKey", value = "the search key", required = true),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),
    })
    @GetMapping("/pass/list_outstanding_proposal_request")
    public RestResponse<IPage<ProposalCard>> listOutstandingProposal(@RequestParam(value="isAscendingOrderLike") Boolean isAscendingOrderLike,
                                                                          @RequestParam(value = "isAscendingOrderTime") Boolean isAscendingOrderTime,
                                                                          @RequestParam(value = "searchKey",required = false) String searchKey,
                                                                          @RequestParam(value = "pageNo") Integer pageNo,
                                                                          @RequestParam(value = "pageSize") Integer pageSize){
        IPage<ProposalCard> proposalInfoVOS = proposalService.listOutstandingProposal(isAscendingOrderLike, isAscendingOrderTime, searchKey,pageNo,pageSize);
        return RestResponse.success(proposalInfoVOS);
    }


    @GetMapping("/list_project_proposals")
    @ApiOperation("list project proposals")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId",value = "project ID",required = true),
            @ApiImplicitParam(name = "isPick", value = "the status of is pick",required = true),
            @ApiImplicitParam(name = "isAscendingOrder", value = "is the order by ascending", required = true),
            @ApiImplicitParam(name = "whatOrder", value = "sort by what order", required = true),
            @ApiImplicitParam(name = "searchKey", value = "the search key", required = true),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),
    })
    public RestResponse<IPage<ProjectProposalInfoVO>> listProjectProposals (@RequestParam(value = "projectId") Integer projectId,
                                                                            @RequestParam(value = "isPick") Integer isPick,
                                                                            @RequestParam(value="isAscendingOrder") Boolean isAscendingOrder,
                                                                            @RequestParam(value = "whatOrder") String whatOrder,
                                                                            @RequestParam(value = "searchKey",required = false) String searchKey,
                                                                            @RequestParam(value = "pageNo") Integer pageNo,
                                                                            @RequestParam(value = "pageSize") Integer pageSize){
        IPage<ProjectProposalInfoVO> projectProposalInfoVO=proposalService.listProjectProposals(projectId, isPick,isAscendingOrder,whatOrder,searchKey,pageNo,pageSize);
        return RestResponse.success(projectProposalInfoVO);
    }


    @GetMapping("/list_approved_project_proposals")
    @ApiOperation("list approved project proposals")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId",value = "project ID",required = true),
            @ApiImplicitParam(name = "isAscendingOrder", value = "is the order by ascending", required = true),
            @ApiImplicitParam(name = "searchKey", value = "the search key"),
            @ApiImplicitParam(name = "pageNo", value = "pageNo", required = true),
            @ApiImplicitParam(name = "pageSize", value = "pageSize", required = true),
    })
    public RestResponse<IPage<ProjectProposalInfoVO>> listApprovedProjectProposals (@RequestParam(value = "projectId") Integer projectId,
                                                                            @RequestParam(value="isAscendingOrder") Boolean isAscendingOrder,
                                                                            @RequestParam(value = "searchKey",required = false) String searchKey,
                                                                            @RequestParam(value = "pageNo") Integer pageNo,
                                                                            @RequestParam(value = "pageSize") Integer pageSize){
        IPage<ProjectProposalInfoVO> projectProposalInfoVO=proposalService.listApprovedProjectProposals(projectId,isAscendingOrder,searchKey,pageNo,pageSize);
        return RestResponse.success(projectProposalInfoVO);
    }
    @ApiOperation("delete draft proposal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proposalId", value = "proposalId", required = true),
    })
    @PostMapping("/delete_proposal")
    public RestResponse deleteProposal(@RequestParam Integer proposalId,
                                       HttpServletRequest http){
        proposalService.deleteProposal(proposalId,http);
        return RestResponse.success();
    }

    @PostMapping("/batch_process_proposals")
    public RestResponse batchProcessProposals(@RequestBody ProposalBatchProcessRequest request,HttpServletRequest http){
        proposalService.batchProcessProposals(request,http);

        return RestResponse.success();
    }

    @PostMapping("/company_process_proposal")
    @ApiOperation("company process proposal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proposalId",value = "id of proposal",required = true),
            @ApiImplicitParam(name = "rating",value = "rating"),
            @ApiImplicitParam(name = "isPick",value = "the value of proposal is pick"),
            @ApiImplicitParam(name = "comment",value = "the comment given by company")
    })
    public RestResponse companyProcessProposal(@RequestParam(value = "proposalId") Integer proposalId,
                                               @RequestParam(value = "rating", required=false) Integer rating,
                                               @RequestParam(value = "isPick",required = false) Integer isPick,
                                               @RequestParam(value = "comment",required = false) String comment){
        proposalService.companyProcessProposal(proposalId,rating,isPick,comment);
        return RestResponse.success();
    }


    @PostMapping("/commit_approved_proposal")
    @ApiOperation("commit approved proposal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId", value = "id of project")
    })
    public RestResponse commitApprovedProposal(@RequestParam(value = "projectId") Integer projectId){
        proposalService.commitApprovedProposal(projectId);
        return RestResponse.success();
    }

    @GetMapping("/can_submit_proposal")
    @ApiOperation("show the student can submit the proposal in this project")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectId",value = "project ID",required = true),
            })
    public RestResponse<Integer> canSubmitProposal (@RequestParam(value = "projectId") Integer projectId,HttpServletRequest http){
        return RestResponse.success(proposalService.canSubmitProposal(projectId,http));
    }

}
