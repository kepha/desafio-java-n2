package rest.dto.resp;

import domain.entities.Cart;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CartRespDTO {
    public Long id; 
    public String status;   

    public static CartRespDTO fromCartRespDTO(Cart cart) {
        CartRespDTO cartRespDTO = new CartRespDTO();       

        cartRespDTO.id = cart.id;  
        cartRespDTO.status = cart.status.name();      
        
        return cartRespDTO;
    }
}
