package domain.entities;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import domain.enums.OrderStatus;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "t_orders")
public class Order  extends AbstractEntity{
    
    @OneToMany(mappedBy = "order",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    public Set<OrderItems> orderItems; 
    
    @Embedded
    public Address deliveryAddress;
    
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(unique = true)
    public Payment payment;
    
    @OneToOne
    public Cart cart;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public OrderStatus status;

    @Column(name = "total_price")
    public BigDecimal price;
}
