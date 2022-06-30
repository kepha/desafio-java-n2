package rest.dto.req;



import domain.entities.Cart;
import domain.enums.CartStatus;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CartReqDTO {    
    public CustomerReqDTO customer;    
    public String status;
    
    public static Cart toCart(CartReqDTO cartReqDTO){
        Cart cart = new Cart();        
        cart.status = CartStatus.valueOf(cartReqDTO.status);
        cart.customer = CustomerReqDTO.toCustomer(cartReqDTO.customer);
        
        return cart; 
    }
}