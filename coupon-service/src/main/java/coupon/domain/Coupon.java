package coupon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Table(name = "coupons")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="owner", nullable = false)
    private String name;

    @Column(name="owner", nullable = false)
    private String owner;

    @Column(name="type", nullable = false)
    private String type;

    @Column(name="value", nullable = false)
    private int value;

    @Column(name="used", nullable = false)
    private boolean used;

    @Column(name="expire_at", nullable = true)
    private Timestamp expirationDate;

    @Column(name="created_at", nullable = false)
    @CreationTimestamp
    private Timestamp createdDate;

    @Builder
    public Coupon(Long id, String name, String owner, String type, int value, Timestamp expirationDate, Timestamp createdDate) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.type = type;
        this.value = value;
        this.expirationDate = expirationDate;
        this.createdDate = createdDate;
        this.used = false;
    }

    public void verifyExpiration(){
       if(LocalDate.now().isAfter(expirationDate)){
           // throw new CouponExpireException;
       }
    }

    public void verifyUsed(){
        if(used) throw new CouponUsedException;
    }
    public void use(){
        verifyExpiration();
        verifyUsed();
        this.used = true;
    }
}
