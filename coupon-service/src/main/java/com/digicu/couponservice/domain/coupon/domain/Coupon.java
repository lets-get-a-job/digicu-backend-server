package com.digicu.couponservice.domain.coupon.domain;

import com.digicu.couponservice.domain.coupon.exception.CouponAccumulateException;
import com.digicu.couponservice.domain.coupon.exception.CouponExpireException;
import com.digicu.couponservice.domain.coupon.exception.CouponStateException;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "coupons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="coupon_id")
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="owner", nullable = false)
    private String owner;

    @Column(name="issuer", nullable = false)
    private String issuer;

    @Column(name="type", nullable = false)
    private String type;

    @Column(name="value", nullable = false)
    private int value;

    @Enumerated(EnumType.STRING)
    @Column(name="state", nullable = false)
    private CouponState state;

    @Column(name="count", nullable = false)
    private int count;

    @Column(name="goal", nullable = false)
    private int goal;

    @Column(name="expire_at", nullable = true)
    private LocalDate expirationDate;

    @Column(name="created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name="trade_id", nullable = true)
    private Long tradeId;

    @Builder
    public Coupon(String name, String owner, String issuer, String type, int value, int goal, int count, LocalDate expirationDate) {
        this.name = name;
        this.owner = owner;
        this.issuer = issuer;
        this.type = type;
        this.state = CouponState.NORMAL;
        this.value = value;
        this.goal = goal;
        this.count = count;
        this.expirationDate = expirationDate;
        this.tradeId = null;
    }

    public void verifyExpiration(){
       if(LocalDate.now().isAfter(expirationDate)){
           throw new CouponExpireException();
       }
    }

    public void verifyAspectState(final List<CouponState> aspects){
        boolean satisfy = false;
        for(CouponState aspect : aspects){
            if(this.state == aspect){
                satisfy = true;
            }
        }
        if(!satisfy){
            throw CouponStateException.of(this.state);
        }
    }
    public void verifyAspectNotState(final CouponState aspect){
        if(this.state == aspect){
            throw CouponStateException.of(this.state);
        }
    }

    public void verifyFull(final int numAcc){
        if(count + numAcc > goal){
            throw new CouponAccumulateException();
        }
    }

    public void use(){
//        verifyExpiration();
        verifyAspectState(Arrays.asList(CouponState.DONE));
        this.state = CouponState.USED;
    }

    public void accumulate(final int numAcc){
//        verifyExpiration();
        verifyAspectState(Arrays.asList(CouponState.NORMAL));
        verifyFull(numAcc);
        this.count += numAcc;
        if(this.count == this.goal){
            this.state = CouponState.DONE;
        }
    }

    public void setTradeState(final CouponState state){
        switch(state){
            case DONE :
                verifyAspectState(Arrays.asList(CouponState.TRADING, CouponState.TRADING_REQ));
                break;
            case TRADING :
            case TRADING_REQ :
                verifyAspectState(Arrays.asList(CouponState.DONE));
                break;
        }
        this.state = state;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setTradeId(Long tradeId) {
        this.tradeId = tradeId;
    }
}
