package repository;


import javax.enterprise.context.ApplicationScoped;

import domain.entities.Payment;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PaymentRepository implements PanacheRepository<Payment> {

}
