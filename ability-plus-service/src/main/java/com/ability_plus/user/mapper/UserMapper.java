package com.ability_plus.user.mapper;

import com.ability_plus.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
@Mapper
public interface UserMapper extends BaseMapper<User>, MPJBaseMapper<User> {


}
