package com.ability_plus.user.service;


import com.ability_plus.proposal.entity.VO.ProposalInfoVO;
import com.ability_plus.user.entity.StudentFollowing;
import com.ability_plus.user.entity.VO.StudentFollowingVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.base.MPJBaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
public interface IStudentFollowingService extends IService<StudentFollowing>, MPJBaseService<StudentFollowing> {
    /**
     * list student's following
     * @param http
     * @return
     */
    List<StudentFollowingVO> listStudentFollowings(HttpServletRequest http);

    /**
     * follow a company
     * @param companyId
     * @param http
     */
    void followCompany(Integer companyId, HttpServletRequest http);

    /**
     * unfollow a company
     * @param companyId
     * @param http
     */
    void unFollowCompany(Integer companyId, HttpServletRequest http);
}
