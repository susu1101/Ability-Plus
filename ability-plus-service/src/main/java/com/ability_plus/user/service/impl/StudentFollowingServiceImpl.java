package com.ability_plus.user.service.impl;


import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.projectRequest.mapper.ProjectRequestMapper;
import com.ability_plus.system.entity.CheckException;
import com.ability_plus.user.entity.StudentFollowing;
import com.ability_plus.user.entity.User;
import com.ability_plus.user.entity.UserPOJO;
import com.ability_plus.user.entity.VO.StudentFollowingVO;
import com.ability_plus.user.mapper.StudentFollowingMapper;
import com.ability_plus.user.service.IStudentFollowingService;
import com.ability_plus.user.service.IUserService;
import com.ability_plus.utils.UserUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;
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
    @Autowired
    IUserService userService;

    @Resource
    StudentFollowingMapper studentFollowingMapper;

    @Override
    public List<StudentFollowingVO> listStudentFollowings() {
        MPJLambdaWrapper<StudentFollowing> wrapper = new MPJLambdaWrapper<>();
        wrapper
                .leftJoin(User.class, User::getId, StudentFollowing::getCompanyId)
                .selectAs(User::getFullName, "companyName")
                .selectAs(StudentFollowing::getCompanyId, "companyId")
                .select(StudentFollowing::getFollowTime);

        return studentFollowingMapper.selectJoinList(StudentFollowingVO.class, wrapper);
    }

    @Override
    public Boolean followCompany(Integer companyId, HttpServletRequest http) {
        UserPOJO user = UserUtils.getCurrentUser(http);
        checkNotCompany(user);

        // find if the company has been followed. Return false on found
        QueryWrapper<StudentFollowing> wrapper = findRecord(user.getId(), companyId);
        if (this.list(wrapper).size() > 0) {
            return false;
        }

        // create new student following object
        StudentFollowing studentFollowing = new StudentFollowing();
        studentFollowing.setStudentId(user.getId());
        studentFollowing.setCompanyId(companyId);
        studentFollowing.setFollowTime((int)Instant.now().getEpochSecond());

        this.save(studentFollowing);
        return true;
    }

    @Override
    public void unFollowCompany(Integer companyId, HttpServletRequest http) {
        UserPOJO user = UserUtils.getCurrentUser(http);
        checkNotCompany(user);

        // query wrapper to find the following record
        QueryWrapper<StudentFollowing> wrapper = findRecord(user.getId(), companyId);
        this.remove(wrapper);
    }

    private void checkNotCompany(UserPOJO user) {
        if (user.getIsCompany()){
            throw new CheckException("Company do not have permission to execute this operation");
        }
    }

    private QueryWrapper<StudentFollowing> findRecord(int studentId, int companyId) {
        QueryWrapper<StudentFollowing> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", studentId);
        wrapper.eq("company_id", companyId);
        return wrapper;
    }
}
