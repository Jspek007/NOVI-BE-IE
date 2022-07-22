package johan.spekman.novibeie.module_promotion.util;

import johan.spekman.novibeie.module_promotion.PromotionType;
import johan.spekman.novibeie.module_promotion.dto.PromotionDto;
import johan.spekman.novibeie.module_promotion.model.Promotion;
import johan.spekman.novibeie.module_promotion.model.Voucher;
import johan.spekman.novibeie.utililies.CreateTimeStamp;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class PromotionUtil {

    private final CreateTimeStamp createTimeStamp;

    public PromotionUtil(CreateTimeStamp createTimeStamp) {
        this.createTimeStamp = createTimeStamp;
    }

    public PromotionType getPromotionType(PromotionDto promotionDto) {
        return promotionDto.getPromotionType();
    }

    public boolean isFixedDiscount(PromotionDto promotionDto) {
        if (promotionDto.isFixedDiscount()) {
            return true;
        } else {
            return false;
        }
    }

    public Promotion preparePromotion(Voucher voucher, PromotionDto promotionDto) throws ParseException {
        Promotion promotion = voucher;

        promotion.setPromotionName(promotionDto.getPromotionName());
        promotion.setCreatedAtDate(createTimeStamp.createTimeStamp());
        promotion.setPromotionType(getPromotionType(promotionDto));

        return voucher;
    }
}
