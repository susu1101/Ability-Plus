package com.ability_plus.proposal.service.impl;


import com.ability_plus.proposal.entity.PO.ProposalCreatePO;
import com.ability_plus.proposal.entity.PO.ProposalEditPO;
import com.ability_plus.proposal.entity.Proposal;
import com.ability_plus.proposal.mapper.ProposalMapper;
import com.ability_plus.proposal.service.IProposalService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
@Service
public class ProposalServiceImpl extends ServiceImpl<ProposalMapper, Proposal> implements IProposalService {


    @Override
    public Integer createProposal(ProposalCreatePO po) {
        return 0;
    }

    @Override
    public Boolean canEditProposal(Integer proposalId) {
        return null;
    }

    @Override
    public void editProposal(ProposalEditPO po) {
        return ;
    }
}
