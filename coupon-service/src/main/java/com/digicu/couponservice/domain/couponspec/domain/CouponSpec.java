package com.digicu.couponservice.domain.couponspec.domain;
import com.digicu.couponservice.domain.couponspec.dto.CouponSpecUpdateRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.*;

@Table(name = "coupon_spec")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class CouponSpec {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="goal", nullable = false)
    private int goal;

    @Column(name="type", nullable = false)
    private String type;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="period", nullable = false)
    private int period;

    @Column(name="value", nullable = false)
    private int value;

    @Column(name="owner", nullable = false)
    private String owner;

    @Builder
    public CouponSpec(Long id, int goal, String type, String name, int period, int value, String owner) {
        this.id = id;
        this.goal = goal;
        this.type = type;
        this.name = name;
        this.period = period;
        this.value = value;
        this.owner = owner;
    }

    public void update(CouponSpecUpdateRequest dto){
        if(dto.getName() != null){
            this.name = dto.getName();
        }
        if(dto.getGoal() != 0){
            this.goal = dto.getGoal();
        }
        if(dto.getPeriod() != 0){
            this.period = dto.getPeriod();
        }
        if(dto.getValue() != 0){
            this.value = dto.getValue();
        }
        if(dto.getType() != null){
            this.type = dto.getType();
        }
    }
}