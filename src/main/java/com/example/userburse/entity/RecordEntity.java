package com.example.userburse.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ub_record")
public class RecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private BigDecimal count;
    /**
     * 1--增加 2--减少
     * @see RecordType
     */
    private Integer type;
    @Column(name = "create_time")
    private LocalDateTime createTime;

    public enum RecordType {
        Increase(1),
        Decrease(2);

        private final int code;

        RecordType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public Long getId() {
        return id;
    }

    public RecordEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public RecordEntity setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public BigDecimal getCount() {
        return count;
    }

    public RecordEntity setCount(BigDecimal count) {
        this.count = count;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public RecordEntity setType(Integer type) {
        this.type = type;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public RecordEntity setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }
}
