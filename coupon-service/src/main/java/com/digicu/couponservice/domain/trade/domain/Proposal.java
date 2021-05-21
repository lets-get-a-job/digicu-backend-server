package com.digicu.couponservice.domain.trade.domain;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "proposals")
@Data
@NoArgsConstructor
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="proposal_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="trade_id")
    private Trade trade;

    @Column(name="owner", nullable = false)
    private String owner;

    @OneToOne
    @JoinColumn(name="id")
    private Coupon coupon;
}
