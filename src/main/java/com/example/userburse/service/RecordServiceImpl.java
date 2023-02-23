package com.example.userburse.service;

import com.example.userburse.entity.RecordEntity;
import com.example.userburse.repoistory.RecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author: li-guohao
 */
@Service
public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;

    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public List<RecordEntity> findRecord(Long uid) {
        Assert.isTrue(uid > 0, "'uid' must gt 0.");
        return recordRepository.findAllByUserId(uid);
    }
}
