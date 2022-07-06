package com.ability_plus.user.service.impl;


import com.ability_plus.user.entity.StudentFollowing;
import com.ability_plus.user.entity.VO.StudentFollowingVO;
import com.ability_plus.user.mapper.StudentFollowingMapper;
import com.ability_plus.user.service.IStudentFollowingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
@Service
public class StudentFollowingServiceImpl extends ServiceImpl<StudentFollowingMapper, StudentFollowing> implements IStudentFollowingService {
    public StudentFollowingServiceImpl() {
    }

    @Override
    public List<StudentFollowingVO> listStudentFollowings() { return null; }
}
