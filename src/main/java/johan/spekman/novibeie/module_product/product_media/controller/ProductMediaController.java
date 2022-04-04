package johan.spekman.novibeie.module_product.product_media.controller;

import johan.spekman.novibeie.exceptions.ApiRequestException;
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
            throw new ApiRequestException("Uploading file has failed: " + exception.getMessage());
        }
    }

    @PostMapping(path = "/upload/mass/{sku}")
    public ResponseEntity<Object> uploadMultipleFiles(@PathVariable("sku") String sku,
                                                      MultipartFile[] files) {
        try {
            productMediaService.storeMultipleFiles(files, sku);
            return ResponseEntity.ok().body("Files uploaded: " + files.length);
        } catch (IOException exception) {
            throw new ApiRequestException("Uploading media files has failed" + exception.getMessage());
        }
    }

    @GetMapping(path = "/download/{fileId}")
    public ResponseEntity<Object> getProductMedia(@PathVariable Long fileId) {
        try {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(productMediaService.getMediaFile(fileId));
        } catch (Exception exception) {
            throw new ApiRequestException("Downloading media file has failed " +  exception.getMessage());
        }
    }

    @GetMapping(path = "/download/all/{sku}")
    public ResponseEntity<Object> getAllMediaBySku(@PathVariable String sku) {
        /*
            Will return the byte[] of all the media that is stored in the database per SKU
         */
        try {
            return ResponseEntity.ok().contentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE)).body(productMediaService.getAllMediaBySku(sku));
        } catch (Exception exception) {
            throw new ApiRequestException("Downloading media files has failed " + exception.getMessage());
        }
    }
}
