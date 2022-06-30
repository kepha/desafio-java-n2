package rest.dto.resp;


import domain.entities.OrderItems;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsRespDTO {
    public Long id;
    public Integer quantity;
    
    public static OrderItemsRespDTO fromOrderItemsRespDTO(OrderItems orderItems) {
        OrderItemsRespDTO customerRespDTO = new OrderItemsRespDTO();
        
        customerRespDTO.id = orderItems.id;
        customerRespDTO.quantity = orderItems.quantity;
        return customerRespDTO;
    }
}
