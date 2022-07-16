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

    private Integer proposalId;

    private String title;

    private Integer projectId;
    private String shortDescription;

    @ApiModelProperty("extra data in json")
    private Map<String, String> extraData;

    private Boolean isDraft;

}
