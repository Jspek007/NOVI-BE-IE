package johan.spekman.novibeie.module_promotion.repository;

import johan.spekman.novibeie.module_promotion.model.Promotion;
import johan.spekman.novibeie.module_promotion.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Voucher, Long> {
    Promotion findByVoucherCode(String voucherCode);
}
