package com.ability_plus.projectRequest.entity.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;

@ApiModel("create project params")
public class ProjectCreatePO {
    @ApiModelProperty("title of project request")
    private String title;

    @ApiModelProperty("the area of project")
    private Integer categoryType;

    @ApiModelProperty("due of proposal collection")
    private Integer proposalDue;

    @ApiModelProperty("due of solution collection")
    private Integer solutionDue;

    @ApiModelProperty("is draft")
    private Boolean isDraft;

    @ApiModelProperty("extra data in json")
    private Map<String, String> extraData;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public Integer getProposalDue() {
        return proposalDue;
    }

    public void setProposalDue(Integer proposalDue) {
        this.proposalDue = proposalDue;
    }

    public Integer getSolutionDue() {
        return solutionDue;
    }

    public void setSolutionDue(Integer solutionDue) {
        this.solutionDue = solutionDue;
    }

    public Boolean getDraft() {
        return isDraft;
    }

    public void setDraft(Boolean draft) {
        isDraft = draft;
    }

    public Map<String, String> getExtraData() {
        return extraData;
    }

    public void setExtraData(Map<String, String> extraData) {
        this.extraData = extraData;
    }
}
