package johan.spekman.novibeie.module_promotion.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_promotion.PromotionType;
import johan.spekman.novibeie.module_promotion.dto.PromotionDto;
import johan.spekman.novibeie.module_promotion.model.Promotion;
import johan.spekman.novibeie.module_promotion.util.PromotionUtil;
import johan.spekman.novibeie.utililies.InputValidation;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.text.ParseException;

@Service
public class PromotionServiceImpl implements PromotionService{

    private final PromotionUtil promotionUtil;
    private final VoucherService voucherService;
    private final InputValidation inputValidation;

    public PromotionServiceImpl(PromotionUtil promotionUtil, VoucherService voucherService, InputValidation inputValidation) {
        this.promotionUtil = promotionUtil;
        this.voucherService = voucherService;
        this.inputValidation = inputValidation;
    }

    public Promotion createPromotion(PromotionDto promotionDto, BindingResult bindingResult) {

        if (inputValidation.validate(bindingResult) != null) {
            throw new ApiRequestException(bindingResult.getFieldError().toString());
        } else {
            switch (promotionDto.getPromotionType()) {
                case VOUCHER -> {
                    return voucherService.createNewVoucher(promotionDto);
                }
                case CART_RULE -> {
                    // TODO - implement cart rule methods
                    break;
                }
                case PRODUCT_RULE -> {
                    // TODO - implement product rule methods
                    System.out.println("error");
                    break;
                }
            }
        }
        return null;
    }
}
