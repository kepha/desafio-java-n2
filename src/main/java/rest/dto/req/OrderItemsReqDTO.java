package rest.dto.req;

import javax.validation.constraints.NotNull;

public class OrderItemsReqDTO {

    @NotNull(message = "quantity is required!")
    public Integer quantity;

    @NotNull(message = "productId is required!")
    public Long productId;

    @NotNull(message = "orderId is required!")
    public Long orderId;

    public OrderItemsReqDTO(Integer quantity, Long productId, Long orderId) {
        this.quantity = quantity;
        this.productId = productId;
        this.orderId = orderId;
    }

    public OrderItemsReqDTO() {
    }
}
