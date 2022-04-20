package johan.spekman.novibeie.module_sales.creditmemo.service;

import johan.spekman.novibeie.module_sales.creditmemo.model.Creditmemo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface CreditmemoService {

    Creditmemo processCreditmemoRequest(@PathVariable("orderId") Long orderId,
                                        @RequestBody String[] skus);

    void createCreditmemo();
}
