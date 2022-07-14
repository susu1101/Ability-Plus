package com.ability_plus.proposal.entity.VO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sjx
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentMyProposalVO extends ProposalCard{
    private String status;
}
