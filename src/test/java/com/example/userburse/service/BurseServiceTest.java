package com.example.userburse.service;

import com.example.userburse.entity.BurseEntity;
import com.example.userburse.entity.UserEntity;
import com.example.userburse.repoistory.BurseRepository;
import com.example.userburse.repoistory.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BurseServiceTest {

    @Autowired
    BurseService burseService;
    @Autowired
    BurseRepository burseRepository;


    @AfterEach
    void tearDown() {
        burseRepository.deleteAll();
    }


    @Test
    void findBalanceByUid() {
        Long uid = Long.MAX_VALUE;

        BigDecimal balance ;
        try {
            balance = burseService.findBalanceByUid(uid);
        } catch (Exception e) {
            Assertions.assertThat(e).isInstanceOf(RuntimeException.class);
            Assertions.assertThat(e.getMessage()).contains("Burse not exists for uid=");
        }

        BurseEntity burse =  new BurseEntity();
        BigDecimal exceptBalance = BigDecimal.valueOf(new Random().nextLong());
        burse.setBalance(exceptBalance);
        burse.setUserId(uid);
        burseRepository.saveAndFlush(burse);

        balance = burseService.findBalanceByUid(uid);
        Assertions.assertThat(balance).isNotNull();
        Assertions.assertThat(balance).isEqualByComparingTo(exceptBalance);
    }

    @Test
    void expend() {
        Long uid = Long.MAX_VALUE;
        BurseEntity burse =  new BurseEntity();
        BigDecimal oldBalance = BigDecimal.valueOf(new Random().nextLong());
        burse.setBalance(oldBalance);
        burse.setUserId(uid);
        burseRepository.saveAndFlush(burse);

        BigDecimal count = BigDecimal.valueOf(new Random().nextInt(1, 10000));
        burseService.expend(uid, count);
        BigDecimal newBalance = burseService.findBalanceByUid(uid);
        Assertions.assertThat(oldBalance.subtract(count)).isEqualByComparingTo(newBalance);
    }

    @Test
    void refund() {
        Long uid = Long.MAX_VALUE;
        BurseEntity burse =  new BurseEntity();
        BigDecimal oldBalance = BigDecimal.valueOf(new Random().nextLong());
        burse.setBalance(oldBalance);
        burse.setUserId(uid);
        burseRepository.saveAndFlush(burse);


        BigDecimal count = BigDecimal.valueOf(new Random().nextInt(1, 10000));
        burseService.refund(uid, count);
        BigDecimal newBalance = burseService.findBalanceByUid(uid);
        Assertions.assertThat(oldBalance.add(count)).isEqualByComparingTo(newBalance);
    }
}