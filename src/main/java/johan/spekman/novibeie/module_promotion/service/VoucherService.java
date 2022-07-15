package johan.spekman.novibeie.module_promotion.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_promotion.dto.PromotionDto;
import johan.spekman.novibeie.module_promotion.model.Voucher;
import johan.spekman.novibeie.module_promotion.repository.PromotionRepository;
import johan.spekman.novibeie.module_promotion.util.PromotionUtil;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
    private final PromotionRepository promotionRepository;
    private final PromotionUtil promotionUtil;

    public VoucherService(PromotionRepository promotionRepository, PromotionUtil promotionUtil) {
        this.promotionRepository = promotionRepository;
        this.promotionUtil = promotionUtil;
    }

    public Voucher createNewVoucher(PromotionDto promotionDto) {
        Voucher voucher = new Voucher();

        if (promotionUtil.isFixedDiscount(promotionDto)) {
            voucher.setFixedDiscount(true);
        } else {
            voucher.setFixedDiscount(false);
        }
        try {
            promotionUtil.preparePromotion(voucher, promotionDto);
            voucher.setVoucherCode(promotionDto.getPromotionName());
            voucher.setDiscountAmount(promotionDto.getDiscountAmount());
        } catch (Exception exception) {
            throw new ApiRequestException(exception.getMessage());
        }
        promotionRepository.save(voucher);
        return voucher;
    }
}

