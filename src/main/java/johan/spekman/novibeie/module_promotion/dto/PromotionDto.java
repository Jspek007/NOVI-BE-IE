package johan.spekman.novibeie.module_promotion.dto;
import johan.spekman.novibeie.module_promotion.PromotionType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PromotionDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;
    @NotBlank
    private String promotionName;

    private PromotionType promotionType;
    @NotNull
    private boolean isFixedDiscount;

    private int discountPercentage;

    private int discountAmount;

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public boolean isFixedDiscount() {
        return isFixedDiscount;
    }

    public void setFixedDiscount(boolean fixedDiscount) {
        isFixedDiscount = fixedDiscount;
    }

    public void setPromotionType(PromotionType promotionType) {
        this.promotionType = promotionType;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }
}
