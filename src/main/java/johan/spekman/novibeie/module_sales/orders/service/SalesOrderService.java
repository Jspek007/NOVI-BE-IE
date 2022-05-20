package johan.spekman.novibeie.module_sales.orders.service;

import johan.spekman.novibeie.module_sales.orders.dto.SalesOrderItemDto;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

public interface SalesOrderService {
    SalesOrder createOrder(@Valid @RequestBody SalesOrderItemDto salesOrderItemDto,
            BindingResult bindingResult) throws ParseException;

    List<SalesOrder> getOrdersByCustomerEmail(@RequestParam("email") String email);
    List<SalesOrder> getAllOrders();
}
