package com.ability_plus.proposal.entity.VO;

import lombok.Data;

/**
 * @author pan
 */
@Data
public class ProjectProposalInfoVO {
    private Integer id;
    private String title;
    private String oneSentenceDescription;
    private Integer authorId;
    private String authorName;
    private Integer rating;
    private String comment;
    private Integer isPick;
    private Integer likeNum;
}