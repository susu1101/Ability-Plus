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
    private String title;

    private Integer categoryType;

    private Integer proposalDue;

    private Integer solutionDue;

    private Boolean isDraft;

    private Map<String, String> extraData;

}
