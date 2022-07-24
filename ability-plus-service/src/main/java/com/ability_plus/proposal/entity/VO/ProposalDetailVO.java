package com.ability_plus.proposal.entity.VO;

import com.ability_plus.proposal.entity.Proposal;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author sjx
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProposalDetailVO extends Proposal {
    private String creatorName;
    private Integer projectId;
    private String projectName;
}
