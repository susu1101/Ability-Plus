package com.ability_plus.proposal.entity.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author sjx
 */
@ApiModel("create proposal params")
public class ProposalPO {

    @ApiModelProperty("title of proposal")
    String title;

    @ApiModelProperty("one sentence description")
    String shortDescription;

    @ApiModelProperty("extra data in json")
    Map<String, String> extraData;

    @ApiModelProperty("is draft")
    Boolean isDraft;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public Map<String, String> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, String> extraData) {
        this.extraData = extraData;
    }

    public Boolean getDraft() {
        return isDraft;
    }

    public void setDraft(Boolean draft) {
        isDraft = draft;
    }
}
