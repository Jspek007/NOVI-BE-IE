package johan.spekman.novibeie.module_orders.service;

import johan.spekman.novibeie.module_orders.dto.SalesOrderItemDto;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.text.ParseException;

public interface SalesOrderService {
    void createOrder(@Valid @RequestBody SalesOrderItemDto salesOrderItemDto,
                     BindingResult bindingResult) throws ParseException;
}
