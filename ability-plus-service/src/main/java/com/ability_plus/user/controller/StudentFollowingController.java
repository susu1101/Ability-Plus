package com.ability_plus.user.controller;


import com.ability_plus.proposal.entity.VO.ProposalInfoVO;
import com.ability_plus.user.entity.VO.StudentFollowingVO;
import com.ability_plus.user.service.IStudentFollowingService;
import com.ability_plus.user.service.impl.StudentFollowingServiceImpl;
import com.ability_plus.utils.RestResponse;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/student_following")
public class StudentFollowingController {

    @Autowired
    IStudentFollowingService studentFollowingService;

    @ApiOperation("get students' following companies")
    @GetMapping("/all")
    public RestResponse<List<StudentFollowingVO>> listStudentFollowings(){
        return RestResponse.success(studentFollowingService.listStudentFollowings());
    }
}
