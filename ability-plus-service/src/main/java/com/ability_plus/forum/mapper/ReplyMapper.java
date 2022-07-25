package com.ability_plus.forum.mapper;

import com.ability_plus.forum.entity.Post;
import com.ability_plus.forum.entity.Reply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author sjx
 * @since 2022-07-24
 */
@Mapper
public interface ReplyMapper extends BaseMapper<Reply>, MPJBaseMapper<Reply> {

}
