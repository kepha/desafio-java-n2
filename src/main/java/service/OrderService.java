
package service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import domain.entities.Customer;
import domain.entities.Order;
import domain.enums.CartMsgExcep;
import domain.enums.CustomerMsgExcep;
import domain.enums.EnumExcep;
import domain.enums.OrderMsgExcep;
import domain.enums.OrderStatus;
import exceptions.ExceptionGeneric;
import exceptions.ValidationException;
import io.quarkus.scheduler.Scheduled;
import repository.CartRepository;
import repository.CustomerRepository;
import repository.OrderRepository;
import repository.PaymentRepository;
import rest.dto.req.OrderReqDTO;
import rest.dto.resp.OrderRespDTO;

@ApplicationScoped
public class OrderService {
    @Inject
    OrderRepository orderRepository;

    @Inject
    CartRepository cartRepository;

    @Inject
    CustomerRepository customerRepository;

    @Inject
    PaymentRepository paymentRepository;

    @Inject
    Validator validator;

    @Transactional
    public OrderRespDTO create(OrderReqDTO orderReqDTO) {

        Set<ConstraintViolation<OrderReqDTO>> validate = validator.validate(orderReqDTO);
        if (!validate.isEmpty()) {
            throw new ValidationException("Exception field", validate);
        }

        var cart = cartRepository.findById(orderReqDTO.cartId);

        if (Objects.isNull(cart)) {
            throw new ExceptionGeneric(CartMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }

        Order order = new Order();
        order.cart = cart;
        order = orderReqDTO.toOrder(order, orderReqDTO);

        orderRepository.persist(order);
        return OrderRespDTO.fromOrderRespDTO(order);
    }

    public List<OrderRespDTO> seachAll() {
        var orders = orderRepository.findAll();
        return orders.list()
                .stream()
                .map((orders_) -> OrderRespDTO.fromOrderRespDTO(orders_))
                .collect(Collectors.toList());
    }

    public OrderRespDTO findById(Long id) {
        var order = orderRepository.findById(id);
        if (Objects.isNull(order)) {
            throw new ExceptionGeneric(OrderMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }
        return OrderRespDTO.fromOrderRespDTO(order);
    }

    @Transactional
    @Scheduled(cron = "{cron.expr}", identity = "My service cron")
    public void schenduler() {
        List<Order> orderWaiting = orderRepository.findByStatus(OrderStatus.WAITING);

        for (Order order : orderWaiting) {
            System.out.println("Id:" + order.id + " Created: " + order.created_at + " Status: " + order.status);
            order.status = order.status.AUTHORIZED;
            System.out.println(" AUTHORIZED: " + Instant.now());
        }
    }

    @Transactional
    public void delete(Long id) {
        Order order = orderRepository.findById(id);

        if (Objects.isNull(order)) {
            throw new ExceptionGeneric(OrderMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }
        Optional.ofNullable(order.payment).ifPresent(paymentRepository::delete);
        orderRepository.delete(order);
    }

    public List<OrderRespDTO> findByCartCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId);

        if (Objects.isNull(customer)) {
            throw new ExceptionGeneric(CustomerMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }

        List<Order> orderList = orderRepository.findAllCartCustomer(customer);
        
        return orderList
        .stream()
        .map((order) -> OrderRespDTO.fromOrderRespDTO(order))
        .collect(Collectors.toList());
    }
}
