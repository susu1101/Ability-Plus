package com.ability_plus.proposal.service;

import com.ability_plus.proposal.entity.PO.ProposalCreatePO;
import com.ability_plus.proposal.entity.PO.ProposalEditPO;
import com.ability_plus.proposal.entity.Proposal;

import com.ability_plus.proposal.entity.VO.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.yulichang.base.MPJBaseService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author susu
 * @since 2022-06-30
 */
public interface IProposalService extends IService<Proposal> , MPJBaseService<Proposal> {
    /**
     * create a proposal
     * @param po
     * @return
     */
    public Integer createProposal(ProposalCreatePO po, HttpServletRequest http);

    /**
     * does this user now can edit this proposal
     * @param proposalId
     * @return
     */
    public  Boolean canEditProposal(Integer proposalId,HttpServletRequest http);


    /**
     * edit a proposal
     * @param po
     */
    public void editProposal(ProposalEditPO po,HttpServletRequest http);

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
    ProposalDetailVO getProposalInfo(@RequestParam(value="proposalId") Integer proposalId);

    /**
     * list proposals created by a user for "my proposals" page
     * @param status the required status
     * @param isAscendingOrder
     * @param whatOrder
     * @param searchKey
     * @param pageNo
     * @param pageSize
     * @param http
     * @return
     */
    IPage<StudentMyProposalVO> listMyProposal(String status, Boolean isAscendingOrder, String whatOrder, String searchKey, Integer pageNo, Integer pageSize, HttpServletRequest http);

    /**
     * List outstanding proposals for "popular proposal" page
     * @param isAscendingOrderLike
     * @param isAscendingOrderTime
     * @param searchKey
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage<ProposalCard> listOutstandingProposal(Boolean isAscendingOrderLike, Boolean isAscendingOrderTime, String searchKey, Integer pageNo, Integer pageSize);


    /**
     * list proposals created by a user for "profile" page
     * @param creatorId the user id of the proposal author
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage<ProposalInfoVO> listStudentProfileProposals(Integer creatorId, Integer pageNo, Integer pageSize);


    /**
     * list proposals created by a user for "profile" page using filter
     * @param creatorId the user id of the proposal author
     * @param isAscendingOrderLastModifiedTime
     * @param searchKey
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage<ProposalInfoVO> listStudentProposal(Integer creatorId, Boolean isAscendingOrderLastModifiedTime,String searchKey, Integer pageNo, Integer pageSize);

    /**
     * list proposals in one project request using filter
     * @param projectId
     * @param isAscendingOrder
     * @param whatOrder
     * @param searchKey
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage<ProjectProposalInfoVO> listProjectProposals(Integer projectId, Boolean isAscendingOrder, String whatOrder, String searchKey, Integer pageNo, Integer pageSize);

    /**
     * list approved proposals in one project request using filter
     * @param projectId
     * @param isAscendingOrder
     * @param searchKey
     * @param pageNo
     * @param pageSize
     * @return
     */
    IPage<ProjectProposalInfoVO> listApprovedProjectProposals(Integer projectId, Boolean isAscendingOrder, String searchKey, Integer pageNo, Integer pageSize);

    /**
     * delete draft proposal
     * @param proposalId
     */
    void deleteProposal(Integer proposalId,HttpServletRequest http);
}
