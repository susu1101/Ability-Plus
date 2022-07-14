package com.ability_plus.proposal.entity.VO;

import lombok.Data;

/**
 * @author sjx
 */
@Data
public class ProposalInfoVO {
    private Integer id;
    private String title;
    private String oneSentenceDescription;
    private String projectName;
    private String status;
    private Integer lastModifiedTime;
}
