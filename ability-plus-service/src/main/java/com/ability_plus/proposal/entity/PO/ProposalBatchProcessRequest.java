package com.ability_plus.proposal.entity.PO;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author sjx
 */
@Data
public class ProposalBatchProcessRequest {
    private ArrayList<Integer> ids;
    private Integer projectId;
    private Integer isPick;
}
