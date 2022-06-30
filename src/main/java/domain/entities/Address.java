package domain.entities;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {
    public String zipcode;
    public String street;
    public Integer numberEstablishment;
    public String district;
    public String city;
    public String uf;
    public String country;
}