package com.ability_plus.proposal.entity.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author sjx
 */
@ApiModel("create proposal params")
@Data
public class ProposalEditPO {

    @ApiModelProperty("id of proposal")
    private Integer proposalId;

    @ApiModelProperty("title of proposal")
    private String title;

    @ApiModelProperty("one sentence description")
    private String shortDescription;

    @ApiModelProperty("extra data in json")
    private Map<String, String> extraData;

    @ApiModelProperty("is draft")
    private Boolean isDraft;

}
