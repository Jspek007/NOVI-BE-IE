package johan.spekman.novibeie.module_promotion.controller;

import johan.spekman.novibeie.module_promotion.dto.PromotionDto;
import johan.spekman.novibeie.module_promotion.service.PromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;

@RestController
@RequestMapping(path = "api/v1/promotions")
public class PromotionController {
    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createNewPromotion(@Valid @RequestBody PromotionDto promotionDto,
                                                     BindingResult bindingResult) throws ParseException {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/promotions/create")
                .toUriString());
        return ResponseEntity.created(uri).body(promotionService.createPromotion(promotionDto, bindingResult));
    };
}
