package com.ability_plus.proposal.entity.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author sjx
 */
@ApiModel("create proposal params")
@Data
public class ProposalCreatePO {

    @ApiModelProperty("title of proposal")
    String title;

    @ApiModelProperty("one sentence description")
    String shortDescription;

    @ApiModelProperty("extra data in json")
    Map<String, String> extraData;

    @ApiModelProperty("is draft")
    Boolean isDraft;
    @ApiModelProperty("projectId")
    Integer projectId;
}
