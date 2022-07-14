package com.ability_plus.projectRequest.entity.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;
@Data
@ApiModel("edit project params")
public class ProjectEditPO {

    @ApiModelProperty("id of project request")
    private Integer projectId;

    @ApiModelProperty("title of project request")
    private String title;

    @ApiModelProperty("the area of project")
    private String categoryType;

    @ApiModelProperty("due of proposal collection")
    private Integer proposalDue;

    @ApiModelProperty("due of solution collection")
    private Integer solutionDue;


    @ApiModelProperty("extra data in json")
    private Map<String, String> extraData;

    @ApiModelProperty("contact email")
    private String contactEmail;
}
