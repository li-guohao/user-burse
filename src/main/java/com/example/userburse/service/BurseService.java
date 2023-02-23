package com.example.userburse.service;


import com.example.userburse.entity.BurseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface BurseService {

    /**
     * 查询用户钱包余额
     */
    BigDecimal findBalanceByUid(Long uid);

    /**
     * 用户消费100元的接口
     */
    @Transactional(rollbackFor = Exception.class)
    void expend(Long uid, BigDecimal count);

    /**
     * 用户退款20元接口
     */
    @Transactional(rollbackFor = Exception.class)
    void refund(Long uid, BigDecimal count);

}
