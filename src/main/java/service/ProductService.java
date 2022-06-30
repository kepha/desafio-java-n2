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

import domain.entities.Product;
import domain.enums.EnumExcep;
import domain.enums.ProductMsgExcep;
import exceptions.ExceptionGeneric;
import exceptions.ValidationException;
import repository.ProductRepository;
import rest.dto.req.ProductReqDTO;
import rest.dto.resp.ProductRespDTO;

@ApplicationScoped
public class ProductService {
    @Inject
    ProductRepository productRepository;
    
    @Inject
    Validator validator;

    @Transactional
    public ProductRespDTO create(ProductReqDTO productReqDTO) {
        
        Set<ConstraintViolation<Object>> validate = validator.validate(productReqDTO);

        if (!validate.isEmpty()) {                      
            throw new ValidationException("Exception field", validate);         
        }

        Product product = ProductReqDTO.toProduct(productReqDTO);              
        productRepository.persist(product);

        return ProductRespDTO.fromProductRespDTO(product);
    }

    public List<ProductRespDTO> searchAll() {
        var products = productRepository.findAll();
        return products.list()
        .stream()
        .map((prod) -> ProductRespDTO.fromProductRespDTO(prod))
        .collect(Collectors.toList());
    }

    public ProductRespDTO findById(Long id) {

        Product product = productRepository.findById(id);
        
        if (Objects.isNull(product)) {
            throw new ExceptionGeneric(ProductMsgExcep.NOTFOUND
            .getMsg(),EnumExcep.NOTFOUND
            .getStatus(),"Search error");        }
        return ProductRespDTO.fromProductRespDTO(product);
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id);
        
        if (Objects.isNull(product)) {
            throw new ExceptionGeneric(ProductMsgExcep.NOTFOUND
            .getMsg(),EnumExcep.NOTFOUND
            .getStatus(),"Search error");           }
        productRepository.delete(product);
    }  
    public Long countProducts(){
        return productRepository.count();
    }  
}
