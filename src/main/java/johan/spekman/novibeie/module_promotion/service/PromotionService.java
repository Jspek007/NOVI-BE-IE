package johan.spekman.novibeie.module_promotion.service;

import johan.spekman.novibeie.module_promotion.dto.PromotionDto;
import johan.spekman.novibeie.module_promotion.model.Promotion;
import org.springframework.validation.BindingResult;

import java.text.ParseException;

public interface PromotionService {
    Promotion createPromotion(PromotionDto promotionDto, BindingResult bindingResult) throws ParseException;
}
