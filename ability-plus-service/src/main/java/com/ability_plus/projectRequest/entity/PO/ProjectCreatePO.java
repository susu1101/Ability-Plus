package com.ability_plus.projectRequest.entity.PO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Map;
@Data
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

}
