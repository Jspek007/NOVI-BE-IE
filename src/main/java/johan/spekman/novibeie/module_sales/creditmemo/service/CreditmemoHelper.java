package johan.spekman.novibeie.module_sales.creditmemo.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrderItem;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CreditmemoHelper {
    public boolean checkIfOrderContainsRequestedItems(SalesOrder salesOrder, String[] skus) {
        List<SalesOrderItem> orderItemList = salesOrder.getOrderItemList();

        Arrays.stream(skus).forEach(sku -> {
            if (orderItemList.stream().map(SalesOrderItem::getSku).noneMatch(sku::equals)) {
                throw new ApiRequestException("Product with sku: " + sku + " can not be found on the original order " +
                        "and could not be added to the creditmemo.");
            }
        });
        return true;
    }
}
