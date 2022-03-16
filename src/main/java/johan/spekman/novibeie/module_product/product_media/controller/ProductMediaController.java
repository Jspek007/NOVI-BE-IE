package johan.spekman.novibeie.module_product.product_media.controller;

import johan.spekman.novibeie.module_product.product_media.model.ProductMedia;
import johan.spekman.novibeie.module_product.product_media.repository.ProductMediaRepository;
import johan.spekman.novibeie.module_product.product_media.service.ProductMediaCompressor;
import johan.spekman.novibeie.module_product.product_media.service.ProductMediaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/products/media/gallery")
public class ProductMediaController {
    private final ProductMediaService productMediaService;
    private final ProductMediaRepository productMediaRepository;

    public ProductMediaController(ProductMediaService productMediaService, ProductMediaRepository productMediaRepository) {
        this.productMediaService = productMediaService;
        this.productMediaRepository = productMediaRepository;
    }

    @PostMapping(path = "/upload/{sku}")
    public ResponseEntity<Object> uploadFile(@PathVariable("sku") String sku,
                                             @RequestBody MultipartFile file) throws IOException {
        ProductMedia productMedia = productMediaService.storeFile(file, sku);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/products/media/download/")
                .path(String.valueOf(productMedia.getId()))
                .toUriString();

        return new ResponseEntity<>(fileDownloadUri, HttpStatus.OK);
    }

    @PostMapping(path = "/upload/mass/{sku}")
    public List<ResponseEntity<Object>> uploadMultipleFiles(@PathVariable("sku") String sku,
                                                            @RequestBody MultipartFile[] files
    ) {
        return Arrays.stream(files)
                .map(file -> {
                    try {
                        return uploadFile(sku, file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    @GetMapping(path = "/download/{fileId}")
    public ResponseEntity<Object> getProductMedia(@PathVariable Long fileId) {
        try {
            productMediaService.getMediaFile(fileId);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(productMediaService.getMediaFile(fileId));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage())
        }
    }
}
