package johan.spekman.novibeie.module_customer_address.repository;

import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {
}
