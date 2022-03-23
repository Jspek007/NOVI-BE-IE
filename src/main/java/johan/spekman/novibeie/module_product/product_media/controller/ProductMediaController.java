package johan.spekman.novibeie.module_product.product_media.controller;

import johan.spekman.novibeie.module_product.product_media.model.ProductMedia;
import johan.spekman.novibeie.module_product.product_media.service.ProductMediaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

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
        try {
            ProductMedia productMedia = productMediaService.storeFile(file, sku);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/products/media/download/")
                    .path(String.valueOf(productMedia.getId()))
                    .toUriString();
            return new ResponseEntity<>(fileDownloadUri, HttpStatus.OK);
        } catch (IOException exception) {
            return ResponseEntity.badRequest().body("Invalid file upload: " + exception.getMessage());
        }
    }

    @PostMapping(path = "/upload/mass/{sku}")
    public ResponseEntity<Object> uploadMultipleFiles(@PathVariable("sku") String sku,
                                                      MultipartFile[] files) throws IOException {
        if (productMediaService.storeMultipleFiles(files, sku)) {
            return ResponseEntity.ok().body("Files uploaded: " + files.length);
        } else {
            return ResponseEntity.badRequest().header("Invalid file upload", "File upload" +
                    " has not succeeded").body("Something went wrong while uploading the files!");
        }
    }

    @GetMapping(path = "/download/{fileId}")
    public ResponseEntity<Object> getProductMedia(@PathVariable Long fileId) {
        try {
            productMediaService.getMediaFile(fileId);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(productMediaService.getMediaFile(fileId));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
}
