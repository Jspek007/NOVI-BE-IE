package johan.spekman.novibeie.module_product.product_media.controller;

import johan.spekman.novibeie.module_product.product_media.service.ProductMediaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/products/media/gallery")
public class ProductMediaController {
    private final ProductMediaService productMediaService;

    public ProductMediaController(ProductMediaService productMediaService) {
        this.productMediaService = productMediaService;
    }

    @PostMapping(path = "/upload/{sku}")
    public ResponseEntity<String> uploadFile(@PathVariable("sku") String sku,
                                             @RequestBody MultipartFile file) {
        try {
            productMediaService.storeFile(file, sku);
            return new ResponseEntity<>("Product media has been saved to : " + sku, HttpStatus.OK);
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
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(productMediaService.getMediaFile(fileId));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @GetMapping(path = "/download/all/{sku}")
    public ResponseEntity<Object> getAllMediaBySku(@PathVariable String sku) {
        try {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(productMediaService.getAllMediaBySku(sku));
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
}
