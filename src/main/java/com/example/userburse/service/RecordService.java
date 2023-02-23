package com.example.userburse.service;

import com.example.userburse.entity.RecordEntity;

import java.util.List;

public interface RecordService {

    /**
     * 查询用户钱包金额变动明细的接口
     */
    List<RecordEntity> findRecord(Long uid);
}
