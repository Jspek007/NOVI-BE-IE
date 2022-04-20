package johan.spekman.novibeie.module_sales.creditmemo.service;

import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditmemoHelper {
    public boolean checkIfOrderContainsRequestedItems(SalesOrder salesOrder, String[] skus) {
        List<SalesOrderItem> orderItemList = salesOrder.getOrderItemList();
        if (orderItemList == null) {
            return false;
        }
        for (SalesOrderItem salesOrderItem : orderItemList) {
            for (String sku : skus) {
                if (salesOrderItem.getSku().matches(sku)) {
                    return true;
                }
            }
        }
        return false;
    }
}
