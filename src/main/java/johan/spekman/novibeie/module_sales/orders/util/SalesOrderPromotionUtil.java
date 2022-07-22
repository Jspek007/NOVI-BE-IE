package johan.spekman.novibeie.module_sales.orders.util;

import johan.spekman.novibeie.module_promotion.model.Promotion;
import johan.spekman.novibeie.module_promotion.repository.PromotionRepository;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;
import org.springframework.stereotype.Service;

@Service
public class SalesOrderPromotionUtil {

    private PromotionRepository promotionRepository;

    public SalesOrderPromotionUtil(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public boolean checkIfOrderHasPromotion(SalesOrder salesOrder) {
        if (salesOrder.getVoucherCode() != null) {
            return true;
        } else {
            return false;
        }
    }

    public Promotion getPromotionOnOrder(SalesOrder salesOrder) {
        return promotionRepository.findByVoucherCode(salesOrder.getVoucherCode());
    }

    public double calculateDiscountWithPercentage(SalesOrder salesOrder, Promotion promotion) {
        double discount = ((promotion.getDiscountPercentage() / 100) * salesOrder.getGrandTotal());
        return discount;
    }

    public SalesOrder prepareOrderWithPromotion(SalesOrder salesOrder, Promotion promotion) {
        if (promotion.isFixedDiscount()) {
            double newGrandTotal = (salesOrder.getGrandTotal() - promotion.getDiscountAmount());
            newGrandTotal = Math.round(newGrandTotal * 100);
            newGrandTotal = newGrandTotal/100;
            System.out.println(newGrandTotal);
            salesOrder.setGrandTotal(newGrandTotal);
            salesOrder.setDiscountAmount(promotion.getDiscountAmount());
            return salesOrder;
        } else {
            return null;
        }
    }
}
