package com.ability_plus.proposal.entity.VO;


import lombok.Data;

/**
 * @author sjx
 */
@Data
public class ProposalCard {
    private String title;
    private String oneSentenceDescription;
    private String area;
    private Long lastModifiedTime;
    private String authorName;
    private Integer authorId;
    private String projectName;
    private Integer likeNum;
    private Integer proposalId;
//    private
}
