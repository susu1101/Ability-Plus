package com.ability_plus.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author susu
 * @since 2022-06-25
 */
public class Test implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long testTime;
    @TableId(type = IdType.AUTO)
    private Integer id;

    public Long getTestTime() {
        return testTime;
    }

    public void setTestTime(Long testTime) {
        this.testTime = testTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Test{" +
            "testTime=" + testTime +
            ", id=" + id +
        "}";
    }
}
