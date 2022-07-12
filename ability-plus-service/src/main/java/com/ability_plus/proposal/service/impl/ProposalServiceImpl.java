package com.ability_plus.proposal.service.impl;


import com.ability_plus.proposal.entity.PO.ProposalCreatePO;
import com.ability_plus.proposal.entity.PO.ProposalEditPO;
import com.ability_plus.proposal.entity.Proposal;
import com.ability_plus.proposal.entity.VO.ProposalInfoVO;
import com.ability_plus.proposal.mapper.ProposalMapper;
import com.ability_plus.proposal.service.IProposalService;
import com.ability_plus.user.entity.POJO.UserPOJO;
import com.ability_plus.utils.UserUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
public class ProposalServiceImpl extends ServiceImpl<ProposalMapper, Proposal> implements IProposalService {

    @Override
    public Integer createProposal(ProposalCreatePO po, HttpServletRequest http) {
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

    @Override
    public List<ProposalInfoVO> listProposalRequests(String ranking, Boolean isAscendingOrderTime, String searchKey, Integer pageNo, Integer pageSize) {
        return null;
    }

    @Override
    public List<Integer> selectProposal(List<Integer> ids) {
        return null;
    }

    @Override
    public Proposal getProposalInfo(Integer proposalId) { return null; }

    @Override
    public List<ProposalInfoVO> listStudentProposalRequest(Integer creatorId, String status, Boolean isAscendingOrderTime, String searchKey, Integer pageNo, Integer pageSize) { return null; }

    @Override
    public List<ProposalInfoVO> listOutstandingProposalRequest(Boolean isAscendingOrderLike, Boolean isAscendingOrderTime, String searchKey, Integer pageNo, Integer pageSize) { return null; }

}
