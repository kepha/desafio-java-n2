
package rest.dto.req;

import java.util.Collections;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import domain.entities.Customer;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.jpa.Password;

public class CustomerReqDTO {

    @NotBlank(message = "fistName is required!")
    public String firstName;

    @NotBlank(message = "lastName is required!")
    public String lastName;

    @NotBlank(message = "Phone is required!")
    public String phone;

    @NotBlank(message = "Email is required!")
    @Email(message = "Email invalid")
    public String email;

    @NotBlank(message = "RG is required!")
    public String rg;

    @CPF (message = "CPF is required!")
    @NotNull(message = "CPF is required!")
    public String cpf;

    @NotBlank(message = "birthDate is required!")    
    public String birthDate;

    @Size(min = 8, message = "Must contain at least 8 characters")
    @NotBlank(message = "Password is required!")
    @Password
    public String password;

    public static Customer toCustomer(CustomerReqDTO customerReqDTO) {
    
        Customer customer = new Customer();
        
            customer.firstName = customerReqDTO.firstName;
            customer.lastName = customerReqDTO.lastName;
            customer.rg = customerReqDTO.rg;
            customer.cpf = customerReqDTO.cpf;
            customer.birthDate = customerReqDTO.birthDate;
            customer.phone = customerReqDTO.phone;
            customer.email = customerReqDTO.email;
            customer.password = BcryptUtil.bcryptHash(customerReqDTO.password);
            customer.carts = Collections.emptySet();
            customer.enabled = Boolean.TRUE;
    
            return customer;
    }
}
