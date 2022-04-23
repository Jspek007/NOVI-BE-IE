package johan.spekman.novibeie.module_customer.repository;

import johan.spekman.novibeie.module_customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCustomerId(Long customerId);
    Customer findByEmailAddress(String emailAddress);
    void deleteByEmailAddress(String customerEmail);
}
