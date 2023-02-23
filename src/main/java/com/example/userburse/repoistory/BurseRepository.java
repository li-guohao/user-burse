package com.example.userburse.repoistory;

import com.example.userburse.entity.BurseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BurseRepository extends JpaRepository<BurseEntity, Long> {
    BurseEntity findByUserId(Long userId);
}
