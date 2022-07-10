package com.ability_plus.user.service;


import com.ability_plus.proposal.entity.VO.ProposalInfoVO;
import com.ability_plus.user.entity.StudentFollowing;
import com.ability_plus.user.entity.VO.StudentFollowingVO;
import com.baomidou.mybatisplus.extension.service.IService;

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
public interface IStudentFollowingService extends IService<StudentFollowing> {
    /**
     * List all following companies of a student
     * @return
     */
    List<StudentFollowingVO> listStudentFollowings();

    /**
     * Follow a company
     * @return
     */
    Boolean followCompany(Integer companyId, HttpServletRequest http);
    /**
     * Follow a company
     * @return
     */
    void unFollowCompany(Integer companyId, HttpServletRequest http);
}
