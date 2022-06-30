package rest.dto.resp;


import domain.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CustomerRespDTO {
    public Long id;
    public String firstName;
    public Boolean enabled;    

    public static CustomerRespDTO fromCustomerRespDTO(Customer customer) {
        CustomerRespDTO customerRespDTO = new CustomerRespDTO();

        customerRespDTO.enabled = customer.enabled;
        customerRespDTO.id = customer.id;
        customerRespDTO.firstName = customer.firstName;
        
        return customerRespDTO;
    }
}
