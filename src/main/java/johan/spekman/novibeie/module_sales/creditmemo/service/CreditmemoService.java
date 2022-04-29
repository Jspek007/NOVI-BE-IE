package johan.spekman.novibeie.module_sales.creditmemo.service;

import johan.spekman.novibeie.module_sales.creditmemo.model.Creditmemo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.util.List;

public interface CreditmemoService {
    List<Creditmemo> getCreditByCustomerEmail(@PathVariable("email") String customerEmail);

    Creditmemo processCreditmemoRequest(@PathVariable("orderId") Long orderId,
                                        @RequestBody String[] skus) throws ParseException;
}
