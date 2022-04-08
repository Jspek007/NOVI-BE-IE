package johan.spekman.novibeie.module_orders.service;

import johan.spekman.novibeie.module_orders.dto.SalesOrderItemDto;
import johan.spekman.novibeie.module_orders.model.SalesOrder;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

public interface SalesOrderService {
    void createOrder(@Valid @RequestBody SalesOrderItemDto salesOrderItemDto,
            BindingResult bindingResult) throws ParseException;

    List<SalesOrder> getOrdersByCustomerEmail(@RequestParam("email") String email);
}
