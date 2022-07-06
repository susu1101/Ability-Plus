package com.ability_plus.user.service;


import com.ability_plus.proposal.entity.VO.ProposalInfoVO;
import com.ability_plus.user.entity.StudentFollowing;
import com.ability_plus.user.entity.VO.StudentFollowingVO;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * @param userId
     * @return
     */
    List<StudentFollowingVO> listStudentFollowings(Integer userId);
}
