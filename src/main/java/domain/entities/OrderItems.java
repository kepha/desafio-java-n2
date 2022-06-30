package domain.entities;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_order_items")
public class OrderItems extends AbstractEntity {

    @Column(name = "quantity", nullable = false)
    public Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    public Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonbTransient
    public Order order;
}
