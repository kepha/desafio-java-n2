package rest.dto.req;


import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    @NotBlank(message = "Street is required")
    public String street;

    @NotBlank(message = "District is required")
    public String district;

    @NotBlank(message = "numberEstablishment is required")
    public Integer numberEstablishment;

    @NotBlank(message = "city is required")
    public String city;

    @Size(min = 2, max = 2, message = "Must have two characters")
    @NotBlank(message = "uf is required")
    public String uf;

    @Size(max = 10)
    @Column(name = "zipcode", nullable = false)
    public String zipcode;
    
    @Size(min = 2,max = 2,message = "must have two characters")
    @NotBlank(message = "country is required")
    public String country;
}

    