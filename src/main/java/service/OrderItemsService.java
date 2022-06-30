
package service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import domain.entities.Order;
import domain.entities.OrderItems;
import domain.enums.EnumExcep;
import domain.enums.OrderItemsMsgExcep;
import domain.enums.OrderMsgExcep;
import domain.enums.ProductMsgExcep;
import exceptions.ExceptionGeneric;
import exceptions.ValidationException;
import repository.OrderItemRepository;
import repository.OrderRepository;
import repository.ProductRepository;
import rest.dto.req.OrderItemsReqDTO;
import rest.dto.resp.OrderItemsRespDTO;

@ApplicationScoped
public class OrderItemsService {
    @Inject
    OrderItemRepository orderItemRepository;

    @Inject
    OrderRepository orderRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    Validator validator;

    @Transactional
    public OrderItemsRespDTO create(OrderItemsReqDTO orderItemsReqDTO) {
        Set<ConstraintViolation<Object>> validate = validator.validate(orderItemsReqDTO);

        if (!validate.isEmpty()) {
            throw new ValidationException("Exception field", validate);
        }

        var order = orderRepository.findById(orderItemsReqDTO.orderId);
        var product = productRepository.findById(orderItemsReqDTO.productId);

        if (Objects.isNull(order)) {
            throw new ExceptionGeneric(OrderMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }

        if (Objects.isNull(product)) {
            throw new ExceptionGeneric(ProductMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }

        var orderItems_ = new OrderItems(orderItemsReqDTO.quantity, product, order);
        orderItemRepository.persist(orderItems_);

        order.price = order.price
                .add(orderItems_.product.price
                        .multiply(new BigDecimal(String.valueOf(orderItemsReqDTO.quantity))));

        orderRepository.persist(order);
        return OrderItemsRespDTO.fromOrderItemsRespDTO(orderItems_);
    }

    public List<OrderItemsRespDTO> seachAll() {
        var orderItems = orderItemRepository.findAll();
        return orderItems.list()
                .stream()
                .map((orderItems_) -> OrderItemsRespDTO.fromOrderItemsRespDTO(orderItems_))
                .collect(Collectors.toList());
    }

    public OrderItemsRespDTO findById(Long id) {
        OrderItems orderItem = orderItemRepository.findById(id);

        if (Objects.isNull(orderItem)) {
            throw new ExceptionGeneric(OrderItemsMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }
        return OrderItemsRespDTO.fromOrderItemsRespDTO(orderItem);
    }

    @Transactional
    public void delete(Long id) {
        OrderItems orderItem = orderItemRepository.findById(id);

        if (Objects.isNull(orderItem)) {
            throw new ExceptionGeneric(OrderItemsMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }

        Order order = orderItem.order;
        order.price = order.price.subtract(orderItem.product.price);

        orderItemRepository.deleteById(id);
        order.orderItems.remove(orderItem);

        orderRepository.persist(order);
    }

    public List<OrderItemsRespDTO> findByOrderId(Long orderId) {

        Order order = orderRepository.findById(orderId);               

        if (Objects.isNull(order)) {
            throw new ExceptionGeneric(OrderMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }

        List<OrderItems> listOrderItems = orderItemRepository.findByOrderId(order);

        return listOrderItems
        .stream()
        .map((orderItems) -> OrderItemsRespDTO.fromOrderItemsRespDTO(orderItems))
        .collect(Collectors.toList());
    }
}
