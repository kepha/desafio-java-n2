package rest.dto.resp;


import java.math.BigDecimal;

import domain.entities.Product;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ProductRespDTO {
    public Long id;
    public String name;
    public String description;
    public BigDecimal price;

    public static ProductRespDTO fromProductRespDTO(Product product) {
        ProductRespDTO productRespDTO = new ProductRespDTO();
        productRespDTO.id = product.id;
        productRespDTO.name = product.name;
        productRespDTO.price = product.price;   
        productRespDTO.description = product.description;
        
        return productRespDTO;
    }
}
