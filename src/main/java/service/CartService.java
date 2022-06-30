
package service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import domain.entities.Cart;
import domain.entities.Customer;
import domain.enums.CartMsgExcep;
import domain.enums.CartStatus;
import domain.enums.CustomerMsgExcep;
import domain.enums.EnumExcep;
import exceptions.ExceptionGeneric;
import repository.CartRepository;
import repository.CustomerRepository;
import rest.dto.resp.CartRespDTO;


@ApplicationScoped

public class CartService {
    @Inject
    CartRepository cartRepository;

    @Inject
    CustomerRepository customerRepository;    

    @Transactional
    public CartRespDTO create(Long customerId) {        

        var customer = customerRepository.findById(customerId);

        if (Objects.isNull(customer)) {
            throw new ExceptionGeneric(CartMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }

        if (customer.enabled != true) {
            throw new ExceptionGeneric(CustomerMsgExcep.ENABLED
                    .getMsg(), EnumExcep.BADREQUEST.getStatus(), "Customer is INACTIVE");
        }

        if (!activeCart(customer)) {
            Cart cart = new Cart(customer, CartStatus.NEW);
            cartRepository.persist(cart);
            return CartRespDTO.fromCartRespDTO(cart);
        }

        throw new ExceptionGeneric(CartMsgExcep.ALREADYEXIST
                .getMsg(), EnumExcep.BADREQUEST.getStatus(), "Duplication error");
    }

    public List<CartRespDTO> searchAll() {
        var carts = cartRepository.findAll();
        return carts.list()
                .stream()
                .map((carts_) -> CartRespDTO.fromCartRespDTO(carts_))
                .collect(Collectors.toList());
    }

    public CartRespDTO findById(Long id) {
        Cart cart = cartRepository.findById(id);
        if (Objects.isNull(cart)) {
            throw new ExceptionGeneric(CartMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }
        return CartRespDTO.fromCartRespDTO(cart);
    }

    @Transactional
    public void delete(Long id) {
        Cart cart = cartRepository.findById(id);

        if (Objects.isNull(cart)) {
            throw new ExceptionGeneric(CartMsgExcep.NOTFOUND
                    .getMsg(),
                    EnumExcep.NOTFOUND
                            .getStatus(),
                    "Search error");
        }
        cart.status = CartStatus.CANCELED;
        cartRepository.persist(cart);
    }

    public Boolean activeCart(Customer customer) {

        List<Cart> carts = cartRepository.findByStatusAndCustomerId(CartStatus.NEW, customer);
        if (Objects.nonNull(carts)) {
            if (carts.size() == 1) {
                return true;
            }
        }
        return false;
    }

    public List<CartRespDTO> activesCart() {
        var actives = cartRepository.findByStatus(CartStatus.NEW)
                .stream()
                .map((actives_) -> CartRespDTO.fromCartRespDTO(actives_))
                .collect(Collectors.toList());
        return actives;
    }
}
