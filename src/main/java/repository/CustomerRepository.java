package repository;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import domain.entities.Customer;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer> {

    public Optional<Customer> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }

    public PanacheQuery<Customer> findEnable(Boolean enabled) {
        return find("enabled", enabled);
    }
}
