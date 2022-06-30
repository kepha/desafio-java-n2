package domain.entities;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import domain.enums.PaymentStatus;
import domain.enums.PaymentType;

@Entity
@Table(name = "t_payments")
public class Payment extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    public PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    public PaymentType type;

    public BigDecimal amount;
}
