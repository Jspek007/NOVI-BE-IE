package johan.spekman.novibeie.module_promotion.model;

import johan.spekman.novibeie.module_promotion.PromotionType;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class Promotion {
    private String promotionName;
    @Enumerated(EnumType.STRING)
    private PromotionType promotionType;
    private Date createdAtDate;
    private Date startDate;
    private Date endDate;
    private boolean fixedDiscount;
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

    public void setPromotionType(PromotionType promotionType) {
        this.promotionType = promotionType;
    }

    public Date getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(Date createdAtDate) {
        this.createdAtDate = createdAtDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isFixedDiscount() {
        return fixedDiscount;
    }

    public void setFixedDiscount(boolean fixedDiscount) {
        this.fixedDiscount = fixedDiscount;
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

