package com.digicu.couponservice.domain.coupon.domain;

import com.digicu.couponservice.domain.coupon.exception.CouponAccumulateException;
import com.digicu.couponservice.domain.coupon.exception.CouponExpireException;
import com.digicu.couponservice.domain.coupon.exception.CouponUsedException;
import com.digicu.couponservice.domain.couponspec.domain.CouponSpec;
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
    @Column(name="id")
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

    public void verifyUsed(){
        if(this.state.equals("USED")) {
            throw new CouponUsedException();
        }
    }
    public void use(){
        verifyExpiration();
        verifyUsed();
        this.state = "USED"; //occur dirty check
    }
    public void verifyFull(final int numAcc){
        if(count + numAcc > goal){
            throw new CouponAccumulateException();
        }
    }

    public void accumulate(final int numAcc){
        verifyExpiration();
        verifyUsed();
        verifyFull(numAcc);
        this.count += numAcc;
        if(this.count == this.goal){
            this.state = "DONE";
        }
    }
}
