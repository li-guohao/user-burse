package com.example.userburse.service;

import com.example.userburse.entity.BurseEntity;
import com.example.userburse.entity.RecordEntity;
import com.example.userburse.repoistory.BurseRepository;
import com.example.userburse.repoistory.RecordRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

@SpringBootTest
class RecordServiceTest {

    @Autowired
    RecordService recordService;
    @Autowired
    BurseService burseService;
    @Autowired
    BurseRepository burseRepository;
    @Autowired
    RecordRepository recordRepository;

    @AfterEach
    void tearDown() {
        burseRepository.deleteAll();
        recordRepository.deleteAll();
    }

    @Test
    void findRecordWhenBurseBalanceIncrease() {
        final long uid = new Random().nextInt(0, 10000);

        BurseEntity burse = new BurseEntity();
        burse.setUserId(uid);
        burse.setBalance(BigDecimal.ZERO);
        burseRepository.saveAndFlush(burse);

        List<RecordEntity> records = recordService.findRecord(uid);
        Assertions.assertThat(records).isEmpty();

        BigDecimal count = BigDecimal.valueOf(new Random().nextInt(0, 10000));
        burseService.refund(uid, count);

        records = recordService.findRecord(uid);
        Assertions.assertThat(records).isNotEmpty();
        RecordEntity recordEntity = records.get(0);
        Assertions.assertThat(recordEntity.getCount()).isEqualByComparingTo(count);
        Assertions.assertThat(recordEntity.getType()).isEqualTo(RecordEntity.RecordType.Increase.getCode());
    }

    @Test
    void findRecordWhenBurseBalanceDecrease() {
        final long uid = new Random().nextInt(0, 10000);


        BigDecimal oldBalance = BigDecimal.valueOf(new Random().nextInt(200, 10000));
        BurseEntity burse = new BurseEntity();
        burse.setUserId(uid);
        burse.setBalance(oldBalance);
        burseRepository.saveAndFlush(burse);

        List<RecordEntity> records = recordService.findRecord(uid);
        Assertions.assertThat(records).isEmpty();

        BigDecimal count = BigDecimal.valueOf(new Random().nextInt(0, 10000));
        burseService.expend(uid, count);

        records = recordService.findRecord(uid);
        Assertions.assertThat(records).isNotEmpty();
        RecordEntity recordEntity = records.get(0);
        Assertions.assertThat(recordEntity.getCount()).isEqualByComparingTo(count);
        Assertions.assertThat(recordEntity.getType()).isEqualTo(RecordEntity.RecordType.Decrease.getCode());
    }
}