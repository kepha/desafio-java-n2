package domain.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Table(name = "t_products")
public class Product extends AbstractEntity {

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "description")
    public String description;

    @Column(name = "price", nullable = false)
    public BigDecimal price;
}