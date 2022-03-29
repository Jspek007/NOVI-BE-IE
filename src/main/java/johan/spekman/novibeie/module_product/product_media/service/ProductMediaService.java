package johan.spekman.novibeie.module_product.product_media.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductMediaService {
    void storeFile(MultipartFile file, String sku) throws IOException;
    boolean storeMultipleFiles(MultipartFile[] files, String sku) throws IOException;
    byte[] getMediaFile(Long fileId);
    List<byte[]> getAllMediaBySku(String sku);
}
