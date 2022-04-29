package johan.spekman.novibeie.module_sales.creditmemo.controller;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_sales.creditmemo.service.CreditmemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/sales_orders/creditmemo")
public class CreditmemoController {

    private final CreditmemoService creditmemoService;

    public CreditmemoController(CreditmemoService creditmemoService) {
        this.creditmemoService = creditmemoService;
    }

    @PostMapping(path = "/process/{orderId}")
    public ResponseEntity<Object> createCreditmemo(@PathVariable("orderId") Long orderId, @RequestBody String[] skus) {
        try {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/sales_orders" +
                    "/creditmemo/process/{orderId}").toUriString());
            return ResponseEntity.created(uri).body(creditmemoService.processCreditmemoRequest(orderId, skus));
        } catch (Exception exception) {
            throw new ApiRequestException("Creditmemo could not be processed: " + exception.getMessage());
        }
    }

    @GetMapping(path = "/get/{email}")
    public ResponseEntity<Object> getCreditmemoByCustomerEmail(@PathVariable("email") String customerEmail) throws Exception {
        try {
            return ResponseEntity.ok().body(creditmemoService.getCreditByCustomerEmail(customerEmail));
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }
}
