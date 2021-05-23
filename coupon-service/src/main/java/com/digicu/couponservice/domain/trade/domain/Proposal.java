package com.digicu.couponservice.domain.trade.domain;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "proposals")
@Getter
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

    @OneToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name="coupon_id")
    private Coupon coupon;

    @Builder
    public Proposal(Trade trade, String owner, Coupon coupon) {
        this.trade = trade;
        this.owner = owner;
        this.coupon = coupon;
    }
}
