package com.ability_plus.proposal.entity.VO;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sjx
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProposalInfoVO extends ProposalCard {
    private Integer likeNum;
}
