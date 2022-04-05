package johan.spekman.novibeie.module_orders.controller;

import johan.spekman.novibeie.module_orders.dto.SalesOrderItemDto;
import johan.spekman.novibeie.module_orders.service.SalesOrderService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@RequestMapping("api/v1/sales_orders")
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    public SalesOrderController(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @PostMapping(path = "create")
    public void createOrder(@Valid @RequestBody SalesOrderItemDto salesOrderItemDto,
                            BindingResult bindingResult) throws ParseException {
        salesOrderService.createOrder(salesOrderItemDto, bindingResult);
    }
}
