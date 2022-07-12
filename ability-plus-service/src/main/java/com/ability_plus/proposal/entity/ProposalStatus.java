package com.ability_plus.proposal.entity;

import lombok.Data;

/**
 * @author sjx
 */
@Data
public class ProposalStatus {
    public static String DRAFT="draft";
    public static String SUBMITTED="submitted";
    public static String APPROVING="approving";
    public static String APPROVED="approved";
    public static String REJECTED="REJECTED";

}
