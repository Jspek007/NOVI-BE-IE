package johan.spekman.novibeie.module_sales.orders.controller;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_sales.orders.dto.SalesOrderItemDto;
import johan.spekman.novibeie.module_sales.orders.service.SalesOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("api/v1/sales_orders")
public class SalesOrderController {

    private final SalesOrderService salesOrderService;

    public SalesOrderController(SalesOrderService salesOrderService) {
        this.salesOrderService = salesOrderService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Object> createOrder(@Valid @RequestBody SalesOrderItemDto salesOrderItemDto,
            BindingResult bindingResult) {
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/sales_orders" +
                    "/create").toUriString());
            return ResponseEntity.created(uri).body(salesOrderService.createOrder(salesOrderItemDto, bindingResult));
        } catch (Exception exception) {
            throw new ApiRequestException("Order could not be created: " + exception.getMessage());
        }
    }

    @GetMapping(path = "/get/{email}")
    public ResponseEntity<Object> getOrdersByCustomerEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(salesOrderService.getOrdersByCustomerEmail(email));
    }

    @GetMapping(path = "/get/all")
    public ResponseEntity<Object> getAllOrders() {
        return ResponseEntity.ok(salesOrderService.getAllOrders());
    }
}
