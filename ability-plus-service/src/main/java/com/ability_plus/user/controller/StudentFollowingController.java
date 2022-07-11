package com.ability_plus.user.controller;


import com.ability_plus.proposal.entity.VO.ProposalInfoVO;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.VO.StudentFollowingVO;
import com.ability_plus.user.service.IStudentFollowingService;
import com.ability_plus.user.service.impl.StudentFollowingServiceImpl;
import com.ability_plus.utils.RestResponse;
import io.swagger.annotations.Api;
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
 * @author susu
 * @since 2022-06-30
 */
@RestController
@RequestMapping("/student_following")
@Api(value="student_following")
public class StudentFollowingController {

    @Autowired
    IStudentFollowingService studentFollowingService;

    @ApiOperation("get students' following companies")
    @GetMapping("/all")
    public RestResponse<List<StudentFollowingVO>> listStudentFollowings(HttpServletRequest http){
        return RestResponse.success(studentFollowingService.listStudentFollowings(http));
    }

    @ApiOperation("follow a company")
    @PostMapping("/{id}")
    public RestResponse<List<StudentFollowingVO>> followCompany(@PathVariable("id") Integer id, HttpServletRequest http){
        studentFollowingService.followCompany(id, http);
        return RestResponse.success();
    }

    @ApiOperation("unfollow a company")
    @DeleteMapping("/{id}")
    public RestResponse<List<StudentFollowingVO>> unfollowCompany(@PathVariable("id") Integer id, HttpServletRequest http){
        studentFollowingService.unFollowCompany(id, http);
        return RestResponse.success();
    }

}
