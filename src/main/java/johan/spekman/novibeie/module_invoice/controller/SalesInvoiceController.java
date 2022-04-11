package johan.spekman.novibeie.module_invoice.controller;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_invoice.model.Payment;
import johan.spekman.novibeie.module_invoice.service.SalesOrderInvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales_orders/payment")
public class SalesInvoiceController {

    private final SalesOrderInvoiceService salesOrderInvoiceService;

    public SalesInvoiceController(SalesOrderInvoiceService salesOrderInvoiceService) {
        this.salesOrderInvoiceService = salesOrderInvoiceService;
    }

    @PostMapping(path = "/process/{orderId}")
    public ResponseEntity<Object> processOrderPayment(@PathVariable("orderId") Long orderId,
                                                      @RequestBody Payment request) {
        try {
            return salesOrderInvoiceService.processPayment(orderId, request);
        } catch (Exception exception) {
            throw new ApiRequestException("Payment could not be processed: " + exception.getMessage());
        }
    }
}
