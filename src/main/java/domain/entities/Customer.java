package domain.entities;

import java.util.Set;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_customers")
public class Customer extends AbstractEntity {

    @Column(name = "first_name", nullable = false)
    public String firstName;

    @Column(name = "last_name", nullable = false)
    public String lastName;

    @Column(name = "rg", nullable = false)
    public String rg;

    @Column(name = "cpf", nullable = false)
    public String cpf;

    @Column(name = "birthDate", nullable = false)
    public String birthDate;

    @Column(name = "phone", nullable = false)
    public String phone;

    @Column(name = "email", nullable = false)
    public String email;

    @Column(name = "password", nullable = false)
    public String password;

    @OneToMany(mappedBy = "customer")
    @JsonbTransient
    public Set<Cart> carts;

    @Column(name = "enabled", nullable = false)
    public Boolean enabled;

}
