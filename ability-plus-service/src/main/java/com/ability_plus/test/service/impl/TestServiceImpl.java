package com.ability_plus.test.service.impl;

import com.ability_plus.test.entity.Test;
import com.ability_plus.test.mapper.TestMapper;
import com.ability_plus.test.service.ITestService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author susu
 * @since 2022-06-25
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {
    @Override
    public String recordTest() {
        long l = System.currentTimeMillis();
        Test test = new Test();
        test.setTestTime(l);
        System.out.println('1');
        this.save(test);
        System.out.println('2');
        String text ="good luck have fun";
        return text+l;
    }
}
