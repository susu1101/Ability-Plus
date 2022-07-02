package com.ability_plus.proposal.service;

import com.ability_plus.proposal.entity.PO.ProposalCreatePO;
import com.ability_plus.proposal.entity.PO.ProposalEditPO;
import com.ability_plus.proposal.entity.Proposal;

import com.ability_plus.proposal.entity.VO.ProposalInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    /**
     * list proposal by condition
     * @param ranking
     * @param isAscendingOrderTime
     * @param searchKey
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<ProposalInfoVO> listProposalRequests(String ranking, Boolean isAscendingOrderTime, String searchKey, Integer pageNo, Integer pageSize);

    /**
     * select proposal
     * @param ids
     * @return
     */
    List<Integer> selectProposal(List<Integer> ids);

    /**
     * get proposal detail infomation
     * @param proposalId
     * @return
     */
    Proposal getProposalInfo(@RequestParam(value="proposalId") Integer proposalId);

}
