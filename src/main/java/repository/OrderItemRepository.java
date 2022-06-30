
package repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import domain.entities.Order;
import domain.entities.OrderItems;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class OrderItemRepository implements PanacheRepository<OrderItems> {

    public List<OrderItems> findByOrderId(Order order) {
        var params = Parameters.with("order", order).map();
        var list = find("order=:order", params);

        return list.list();
    }          
}
