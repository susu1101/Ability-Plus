package com.ability_plus.projectRequest.entity;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.ibatis.jdbc.Null;

import java.util.Stack;

/**
 * @author sjx
 */
public class ProjectRequestStatus {
    public static String DRAFT="draft";
    public static String OPEN_FOR_PROPOSAL="open_for_proposal";
    public static String APPROVING="approving";
    public static String OPEN_FOR_SOLUTION="open_for_solution";
    public static String CLOSED="closed";

}
