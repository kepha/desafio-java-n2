
package service;

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
import domain.enums.CustomerMsgExcep;
import domain.enums.EnumExcep;
import exceptions.ExceptionGeneric;
import exceptions.ValidationException;
import repository.CustomerRepository;
import rest.dto.req.CustomerReqDTO;
import rest.dto.resp.CustomerRespDTO;


@ApplicationScoped
public class CustomerService {

    @Inject
    CustomerRepository customerRepository;

    @Inject
    Validator validator;

    @Transactional
    public CustomerRespDTO create(CustomerReqDTO customerReqDTO) {
        Set<ConstraintViolation<Object>> validate = validator.validate(customerReqDTO);

        if (!validate.isEmpty()) {                      
            throw new ValidationException("Exception field", validate);         
        }

        Optional<Customer> customerOptional = customerRepository.findByEmail(customerReqDTO.email);

        if (customerOptional.isPresent()) {
            throw new ExceptionGeneric(CustomerMsgExcep.ALREADYEXIST
            .getMsg(),EnumExcep.BADREQUEST.getStatus(),"Duplication error");
        }      

        Customer customer = CustomerReqDTO.toCustomer(customerReqDTO);
         customerRepository.persist(customer); 
         return CustomerRespDTO.fromCustomerRespDTO(customer);
    }

    public List<CustomerRespDTO> seachAll() {
        var customers = customerRepository.findAll();
        return customers.list()
        .stream()
        .map((customer) -> CustomerRespDTO.fromCustomerRespDTO(customer))
        .collect(Collectors.toList());        
    }

    public CustomerRespDTO findById(Long id) {
        Customer customer = customerRepository.findById(id);

        if (Objects.isNull(customer)) {
            throw new ExceptionGeneric(CustomerMsgExcep.NOTFOUND
            .getMsg(),EnumExcep.NOTFOUND
            .getStatus(),"Search error");
        }
        return CustomerRespDTO.fromCustomerRespDTO(customer);
    }

    @Transactional
    public void delete(Long id) {
        Customer customer = customerRepository.findById(id);

        if (Objects.isNull(customer)) {
            throw new ExceptionGeneric(CustomerMsgExcep.NOTFOUND
            .getMsg(),EnumExcep.NOTFOUND
            .getStatus(),"Search error");
        }
        customer.enabled = false;
    }

    public List<CustomerRespDTO> allActives() { 
        return customerRepository.findEnable(true)
        .list()
        .stream()
        .map((customer) -> CustomerRespDTO.fromCustomerRespDTO(customer))
        .collect(Collectors.toList());
    }

    public List<CustomerRespDTO> allInactives() {
        return customerRepository.findEnable(false)
        .list()
        .stream()
        .map((customer) -> CustomerRespDTO.fromCustomerRespDTO(customer))
        .collect(Collectors.toList());
    }
}
