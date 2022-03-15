package johan.spekman.novibeie.module_product.product_media.controller;

import johan.spekman.novibeie.module_product.product_media.model.ProductMedia;
import johan.spekman.novibeie.module_product.product_media.service.ProductMediaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/products/media/gallery")
public class ProductMediaController {
    private final ProductMediaService productMediaService;

    public ProductMediaController(ProductMediaService productMediaService) {
        this.productMediaService = productMediaService;
    }

    @PostMapping(path = "/upload/{sku}")
    public ResponseEntity<Object> uploadFile(@PathVariable("sku") String sku,
                                     @RequestBody MultipartFile file) {
        ProductMedia productMedia = productMediaService.storeFile(file, sku);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(String.valueOf(productMedia.getId()))
                .toUriString();

        new ResponseEntity<Object>(HttpStatus.OK);
        return null;
    }
}
