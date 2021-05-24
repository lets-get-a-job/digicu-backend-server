package com.digicu.couponservice.domain.trade.domain;

import com.digicu.couponservice.domain.coupon.domain.Coupon;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "trades")
@Getter
@NoArgsConstructor
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="trade_id")
    private Long id;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name="coupon_id")
    private Coupon coupon;

    @Column(name="owner", nullable = false)
    private String owner;

    @Column(name="bound_value", nullable = false)
    private Integer boundValue;

    @Column(name="created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdDate;

    @JsonBackReference
    @OneToMany(mappedBy = "trade" ,cascade = CascadeType.ALL ,orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Proposal> proposals;

    @Builder
    public Trade(Coupon coupon, String owner, Integer boundValue) {
        this.coupon = coupon;
        this.owner = owner;
        this.boundValue = boundValue;
        this.proposals = new ArrayList<Proposal>();
    }

    public void addProposal(Proposal proposal){
        this.proposals.add(proposal);
    }
}