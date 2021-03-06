package johan.spekman.novibeie.module_customer_address.repository;

import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {
    @Query(value = "SELECT * FROM ADDRESSES WHERE parent_id = ?1 AND customer_address_type = ?2 AND default_address =" +
            " true", nativeQuery =
            true)
    CustomerAddress getCustomerAddressByCustomerAndType(Long customerID, String type);
}
