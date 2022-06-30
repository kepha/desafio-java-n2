
package repository;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import domain.entities.Customer;
import domain.entities.Order;
import domain.entities.Payment;
import domain.enums.OrderStatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<Order> {

    public List<Order> findByStatus(OrderStatus waiting) {
        var list = find("status", waiting);
        return list.list();
    }

    public List<Order> findAllCartCustomer(Customer customer) {

        var params = Parameters.with("cart", customer.carts).map();
        var list = find("cart=:cart", params);

        return list.list();
    }

    public Optional<Order> findByPayment(Payment payment) {
        
        var params = Parameters.with("payment",payment).map();
        Optional<Order> order = find("payment=:payment", params).firstResultOptional();

        return order;
    }
}
