package com.digicu.couponservice.domain.coupon.domain;

import com.digicu.couponservice.domain.coupon.exception.CouponAccumulateException;
import com.digicu.couponservice.domain.coupon.exception.CouponExpireException;
import com.digicu.couponservice.domain.coupon.exception.CouponStateException;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    //USED, DONE, NORMAL, TRADING
    @Column(name="state", nullable = false)
    private String state;

    @Column(name="count", nullable = false)
    private int count;

    @Column(name="goal", nullable = false)
    private int goal;

    @Column(name="expire_at", nullable = true)
    private LocalDate expirationDate;

    @Column(name="created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;


    @Builder
    public Coupon(String name, String owner, String issuer, String type, int value, int goal, int count, LocalDate expirationDate) {
        this.name = name;
        this.owner = owner;
        this.issuer = issuer;
        this.type = type;
        this.state = "NORMAL";
        this.value = value;
        this.goal = goal;
        this.count = count;
        this.expirationDate = expirationDate;
    }

    public void verifyExpiration(){
       if(LocalDate.now().isAfter(expirationDate)){
           throw new CouponExpireException();
       }
    }

    public void verifyAspectState(final String aspect){
        if(!this.state.equals(aspect)){
            throw CouponStateException.of(this.state);
        }
    }
    public void verifyAspectNotState(final String aspect){
        if(this.state.equals(aspect)){
            throw CouponStateException.of(this.state);
        }
    }

    public void verifyFull(final int numAcc){
        if(count + numAcc > goal){
            throw new CouponAccumulateException();
        }
    }

    public void use(){
        verifyExpiration();
        verifyAspectState("DONE");
        this.state = "USED";
    }

    public void accumulate(final int numAcc){
        verifyExpiration();
        verifyAspectState("NORMAL");
        verifyFull(numAcc);
        this.count += numAcc;
        if(this.count == this.goal){
            this.state = "DONE";
        }
    }

    public void setTradeState(final String state){
        switch(state){
            case "DONE" :
                verifyAspectState("TRADING");
                break;
            case "TRADING" :
                verifyAspectState("DONE");
                break;
        }
        this.state = state;
    }
}
