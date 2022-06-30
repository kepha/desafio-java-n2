
package rest.dto.resp;


import domain.entities.Payment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PaymentRespDTO {
    public Long id;        
    public Long orderId;
    public String type;    
    public String status;    

    public static PaymentRespDTO fromPaymentRespDTO(Payment payment, Long orderId) {
        PaymentRespDTO paymentRespDTO = new PaymentRespDTO(); 

        paymentRespDTO.id = payment.id;
        paymentRespDTO.type = payment.type.name();        
        paymentRespDTO.status = payment.status.name();
        paymentRespDTO.orderId = orderId;
        
        return paymentRespDTO;
    }

    public static PaymentRespDTO fromPaymentRespDTO(Payment payment) {
        PaymentRespDTO paymentRespDTO = new PaymentRespDTO(); 

        paymentRespDTO.id = payment.id;
        paymentRespDTO.type = payment.type.name();        
        paymentRespDTO.status = payment.status.name();
        
        return paymentRespDTO;
    }
}
