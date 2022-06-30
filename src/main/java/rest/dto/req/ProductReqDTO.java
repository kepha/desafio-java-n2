package rest.dto.req;
import java.math.BigDecimal;

import javax.json.bind.annotation.JsonbNumberFormat;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import domain.entities.Product;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProductReqDTO {

    @NotBlank(message = "Name is required!")
    public String name;

    @NotBlank(message = "Description is required")
    public String description;

    @NotNull
    @Positive
    @Digits(integer = 5, fraction = 2)
    @JsonbNumberFormat("#0.00")
    public BigDecimal price;

    public static Product toProduct(ProductReqDTO productReqDTO) {

        Product product = new Product();
        product.name = productReqDTO.name;
        product.description = productReqDTO.description;
        product.price = productReqDTO.price;

        return product;
    }
}
