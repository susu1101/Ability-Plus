package com.ability_plus.projectRequest.controller;


import com.ability_plus.projectRequest.entity.VO.CommentInfoVO;
import com.ability_plus.projectRequest.entity.VO.ProfileProjectInfoVO;
import com.ability_plus.projectRequest.service.IProjectProposalRecordService;
import com.ability_plus.utils.RestResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/project_proposal_record")
public class ProjectProposalRecordController {

    @Autowired
    IProjectProposalRecordService projectProposalRecordService;
    @GetMapping("/get_info")
    @ApiOperation("get company user's rating, notes and isPick status to a proposal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proposalId", value = "proposalId", required = true),
            @ApiImplicitParam(name = "projectId", value = "projectId", required = true),
    })
    public RestResponse<CommentInfoVO> getInfo(@RequestParam(value = "proposalId") Integer proposalId,
                                               @RequestParam(value = "projectId") Integer projectId){

        return RestResponse.success(projectProposalRecordService.getInfo(proposalId,projectId));
    }
}
