package domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import domain.enums.CartStatus;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "t_carts")
@RequiredArgsConstructor
@AllArgsConstructor
public class Cart extends AbstractEntity {

    @ManyToOne
    public Customer customer;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    public CartStatus status;
}
