package com.digicu.couponservice.domain.couponspec.domain;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "coupon_spec")
@Getter
@Builder
@NoArgsConstructor
@Entity
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
}