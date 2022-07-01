package com.ability_plus.proposal.service;

import com.ability_plus.proposal.entity.PO.ProposalCreatePO;
import com.ability_plus.proposal.entity.PO.ProposalEditPO;
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
    /**
     * create a proposal
     * @param po
     * @return
     */
    public Integer createProposal(ProposalCreatePO po);

    /**
     * does this user now can edit this proposal
     * @param proposalId
     * @return
     */
    public  Boolean canEditProposal(Integer proposalId);


    /**
     * edit a proposal
     * @param po
     */
    public void editProposal(ProposalEditPO po);
}
