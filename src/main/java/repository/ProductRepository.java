package repository;

import javax.enterprise.context.ApplicationScoped;

import domain.entities.Product;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {
    
}
