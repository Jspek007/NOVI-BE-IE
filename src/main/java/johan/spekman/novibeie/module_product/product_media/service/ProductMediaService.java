package johan.spekman.novibeie.module_product.product_media.service;

import johan.spekman.novibeie.module_product.product_media.model.ProductMedia;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductMediaService {
    ProductMedia storeFile(MultipartFile file, String sku) throws IOException;
    byte[] getMediaFile(Long fileId);
}
