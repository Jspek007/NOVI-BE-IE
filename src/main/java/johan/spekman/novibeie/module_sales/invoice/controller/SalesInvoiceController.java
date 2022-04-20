package johan.spekman.novibeie.module_sales.invoice.controller;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_sales.invoice.model.Payment;
import johan.spekman.novibeie.module_sales.invoice.model.SalesInvoice;
import johan.spekman.novibeie.module_sales.invoice.service.SalesInvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/sales_orders/payment")
public class SalesInvoiceController {

    private final SalesInvoiceService salesInvoiceService;

    public SalesInvoiceController(SalesInvoiceService salesInvoiceService) {
        this.salesInvoiceService = salesInvoiceService;
    }

    @PostMapping(path = "/process/{orderId}")
    public ResponseEntity<SalesInvoice> processOrderPayment(@PathVariable("orderId") Long orderId,
                                                            @RequestBody Payment request) {
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/sales_orders" +
                    "/payment/process/{orderId}").toUriString());
            return ResponseEntity.created(uri).body(salesInvoiceService.processPayment(orderId, request));
        } catch (Exception exception) {
            throw new ApiRequestException("Payment could not be processed: " + exception.getMessage());
        }
    }
}
