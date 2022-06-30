
package rest.dto.req;

import java.math.BigDecimal;
import java.util.Collections;

import javax.validation.constraints.NotNull;

import domain.entities.Address;
import domain.entities.Order;
import domain.enums.OrderStatus;

public class OrderReqDTO {
    
    public Address deliveryAddress;

    @NotNull(message = "cartId is required")     
    public Long cartId;

    public static Order toOrder(Order order, OrderReqDTO orderReqDTO) {

        order.price = BigDecimal.ZERO;
        order.status = OrderStatus.WAITING;
        order.payment = null;
        order.orderItems = Collections.emptySet();
        order.deliveryAddress = orderReqDTO.deliveryAddress;

        return order;
    }
}
