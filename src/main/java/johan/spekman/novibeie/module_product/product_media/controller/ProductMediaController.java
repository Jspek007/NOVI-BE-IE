package johan.spekman.novibeie.module_product.product_media.controller;

import johan.spekman.novibeie.module_product.product_media.model.ProductMedia;
import johan.spekman.novibeie.module_product.product_media.service.ProductMediaCompressor;
import johan.spekman.novibeie.module_product.product_media.service.ProductMediaService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
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

@RestController
@RequestMapping("/api/v1/products/media/gallery")
public class ProductMediaController {
    private final ProductMediaService productMediaService;

    public ProductMediaController(ProductMediaService productMediaService) {
        this.productMediaService = productMediaService;
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

    @GetMapping(path = "/download/{sku}")
    public ResponseEntity<byte[]> getProductMedia(@PathVariable String sku) {
        ProductMedia productMedia = productMediaService.getFile(sku);
        byte[] image = ProductMediaCompressor.decompressBytes(productMedia.getData());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(image);
    }
}
