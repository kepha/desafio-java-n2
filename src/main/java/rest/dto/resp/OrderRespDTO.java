package rest.dto.resp;


import java.math.BigDecimal;

import domain.entities.Order;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OrderRespDTO {
    public Long id;
    public String status;   
    public BigDecimal totalPrice; 

    public static OrderRespDTO fromOrderRespDTO(Order order) {
        OrderRespDTO orderRepDTO = new OrderRespDTO(); 
        
        orderRepDTO.id = order.id;
        orderRepDTO.status = order.status.name();  
        orderRepDTO.totalPrice = order.price;      
        return orderRepDTO;
    }
}
