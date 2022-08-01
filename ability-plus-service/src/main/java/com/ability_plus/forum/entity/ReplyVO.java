package com.ability_plus.forum.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReplyVO extends Reply{
    private String replierName;
}
