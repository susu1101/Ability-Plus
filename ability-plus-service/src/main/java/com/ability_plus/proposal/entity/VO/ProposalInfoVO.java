package com.ability_plus.proposal.entity.VO;

import com.ability_plus.utils.CardUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sjx
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProposalInfoVO extends ProposalCard {
    private String status;
}
