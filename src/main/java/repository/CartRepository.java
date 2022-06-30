package repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import domain.entities.Cart;
import domain.entities.Customer;
import domain.enums.CartStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class CartRepository implements PanacheRepository<Cart> {
    public List<Cart> findByStatus(CartStatus New) {
        var list = find("status", New);
        return list.list();
    }

    public List<Cart> findByStatusAndCustomerId(CartStatus status, Customer customer) {
        var params = Parameters.with("status_", status).and("customer", customer).map();
        var list = find("status=:status_ and customer=:customer", params);
        return list.list();
    }
}