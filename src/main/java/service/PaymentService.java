
package service;

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
import domain.entities.Payment;
import domain.enums.EnumExcep;
import domain.enums.OrderMsgExcep;
import domain.enums.PaymentMsgExcep;
import domain.enums.PaymentStatus;
import exceptions.ExceptionGeneric;
import exceptions.ValidationException;
import repository.OrderRepository;
import repository.PaymentRepository;
import rest.dto.req.PaymentReqDTO;
import rest.dto.resp.OrderRespDTO;
import rest.dto.resp.PaymentRespDTO;

@ApplicationScoped
public class PaymentService {
    @Inject
    PaymentRepository paymentRepository;

    @Inject
    OrderRepository orderRepository;

    @Inject
    Validator validator;

    @Transactional
    public PaymentRespDTO create(PaymentReqDTO paymentReqDTO) {

        Set<ConstraintViolation<Object>> validate = validator.validate(paymentReqDTO);

        if (!validate.isEmpty()) {
            throw new ValidationException("Exception field", validate);
        }

        Order order = orderRepository.findById(paymentReqDTO.orderId);

        if (Objects.isNull(order)) {
            throw new ExceptionGeneric(OrderMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }
        order.status = order.status.PAID;

        Payment payment = new Payment();
        payment = PaymentReqDTO.toPayment(paymentReqDTO);
        payment.amount = order.price;
        order.payment = payment;

        paymentRepository.persistAndFlush(payment);
        orderRepository.persistAndFlush(order);

        return PaymentRespDTO.fromPaymentRespDTO(payment, order.id);
    }

    public List<PaymentRespDTO> searchAll() {
        var payments = paymentRepository.findAll();
        return payments.list()
                .stream()
                .map((payments_) -> PaymentRespDTO.fromPaymentRespDTO(payments_))
                .collect(Collectors.toList());
    }

    public PaymentRespDTO findById(Long id) {
        Payment payment = paymentRepository.findById(id);
        if (Objects.isNull(payment)) {
            throw new ExceptionGeneric(PaymentMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }
        return PaymentRespDTO.fromPaymentRespDTO(payment);
    }

    @Transactional
    public void delete(Long id) {
        Payment payment = paymentRepository.findById(id);

        if (Objects.isNull(payment)) {
            throw new ExceptionGeneric(PaymentMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }
        payment.status = PaymentStatus.CANCELED;
    }

    public OrderRespDTO findOrderByPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId);

        if (Objects.isNull(payment)) {
            throw new ExceptionGeneric(PaymentMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }
        Order order = orderRepository.findByPayment(payment)
                .orElseThrow(() -> new ExceptionGeneric(OrderMsgExcep.NOTFOUND
                        .getMsg(),
                        EnumExcep.NOTFOUND
                                .getStatus(),
                        "Search error"));

        return OrderRespDTO.fromOrderRespDTO(order);
    }
}
