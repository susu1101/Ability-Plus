package com.ability_plus.test.service;

import com.ability_plus.test.entity.Test;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author susu
 * @since 2022-06-25
 */
public interface ITestService extends IService<Test> {
    public String recordTest();
}
