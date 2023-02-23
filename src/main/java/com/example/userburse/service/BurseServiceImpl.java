package com.example.userburse.service;

import com.example.userburse.entity.BurseEntity;
import com.example.userburse.entity.RecordEntity;
import com.example.userburse.entity.UserEntity;
import com.example.userburse.repoistory.BurseRepository;
import com.example.userburse.repoistory.RecordRepository;
import com.example.userburse.repoistory.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class BurseServiceImpl implements BurseService {
    private final BurseRepository burseRepository;
    private final RecordRepository recordRepository;

    public BurseServiceImpl(BurseRepository burseRepository, RecordRepository recordRepository) {
        this.burseRepository = burseRepository;
        this.recordRepository = recordRepository;
    }


    @Override
    public BigDecimal findBalanceByUid(Long uid) {
        Assert.isTrue(uid > 0, "'uid' must gt 0.");
        BurseEntity burseEntity = burseRepository.findByUserId(uid);
        if (burseEntity == null) {
            throw new RuntimeException("Burse not exists for uid=" + uid);
        }
        return burseEntity.getBalance();
    }

    @Override
    public void expend(Long uid, BigDecimal count) {
        BurseEntity burseEntity = assertAndGetBurseEntity(uid, count);

        // 减少账户余额
        BigDecimal balance = burseEntity.getBalance();
        burseEntity.setBalance(balance.subtract(count));
        burseRepository.save(burseEntity);

        // 添加流水记录
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setCount(count);
        recordEntity.setUserId(uid);
        recordEntity.setType(RecordEntity.RecordType.Decrease.getCode());
        recordEntity.setCreateTime(LocalDateTime.now());
        recordRepository.save(recordEntity);
    }

    @Override
    public void refund(Long uid, BigDecimal count) {
        BurseEntity burseEntity = assertAndGetBurseEntity(uid, count);

        // 增加账户余额
        BigDecimal balance = burseEntity.getBalance();
        burseEntity.setBalance(balance.add(count));
        burseRepository.save(burseEntity);

        // 添加流水记录
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setCount(count);
        recordEntity.setUserId(uid);
        recordEntity.setType(RecordEntity.RecordType.Increase.getCode());
        recordEntity.setCreateTime(LocalDateTime.now());
        recordRepository.save(recordEntity);
    }

    private BurseEntity assertAndGetBurseEntity(Long uid, BigDecimal count) {
        Assert.isTrue(uid > 0, "'uid' must gt 0.");
        Assert.isTrue(count.doubleValue() > 0, "'count' must gt 0.");
        BurseEntity burseEntity = burseRepository.findByUserId(uid);
        if (burseEntity == null) {
            throw new RuntimeException("Burse not exists for uid=" + uid);
        }
        return burseEntity;
    }

}
