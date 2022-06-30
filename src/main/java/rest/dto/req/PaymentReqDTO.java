
package rest.dto.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import domain.entities.Payment;
import domain.enums.PaymentStatus;
import domain.enums.PaymentType;

public class PaymentReqDTO {
    @NotBlank(message = "Field Inválid")
    public String status;
    
    @NotNull(message = "Field is required")
    public Long orderId;

    @NotBlank(message = "Field Inválid")
    public String type;

    public static Payment toPayment(PaymentReqDTO paymentReqDTO) {
        Payment payment = new Payment();

        payment.type = PaymentType.valueOf(paymentReqDTO.type);
        payment.status = PaymentStatus.valueOf(paymentReqDTO.status);

        return payment;
    }
}
