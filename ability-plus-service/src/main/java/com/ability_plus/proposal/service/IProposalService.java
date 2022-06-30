package com.ability_plus.proposal.service;

import com.ability_plus.proposal.entity.PO.ProposalPO;
import com.ability_plus.proposal.entity.Proposal;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
public interface IProposalService extends IService<Proposal> {
    public void createProjectRequest(ProposalPO po);
}
