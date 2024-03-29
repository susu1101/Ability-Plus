package com.ability_plus.proposal.mapper;


import com.ability_plus.projectRequest.entity.ProjectRequest;
import com.ability_plus.proposal.entity.Proposal;
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
public interface ProposalMapper extends BaseMapper<Proposal>, MPJBaseMapper<Proposal> {

}
