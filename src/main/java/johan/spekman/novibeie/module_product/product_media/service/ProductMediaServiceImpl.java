package johan.spekman.novibeie.module_product.product_media.service;


import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.module_product.product_media.repository.ProductMediaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import johan.spekman.novibeie.module_product.product_media.model.ProductMedia;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Arrays;
import java.util.InvalidPropertiesFormatException;
import java.util.Objects;

@Service
@Transactional
public class ProductMediaServiceImpl implements ProductMediaService {

    private final ProductMediaRepository productMediaRepository;
    private final ProductRepository productRepository;

    public ProductMediaServiceImpl(ProductMediaRepository productMediaRepository,
                                   ProductRepository productRepository) {
        this.productMediaRepository = productMediaRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductMedia storeFile(MultipartFile file, String sku)
            throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String contentType = "image/png";
        // Check for file name
        if (fileName.contains("..")) {
            throw new IOException("Invalid file name");
        }

        /*
            Check for correct content type
            allowed content type is image/png
         */

        if (!Objects.equals(file.getContentType(), contentType)) {
            throw new IOException("Filetype is not correct. Allowed file type is: " + contentType);
        }

        ProductMedia productMedia = new ProductMedia();
        productMedia.setData(ProductMediaCompressor.compressBytes(file.getBytes()));
        productMedia.setFileName(fileName);
        Product product = productRepository.findBySku(sku);
        product.addProductMedia(productMedia);
        return productMediaRepository.save(productMedia);
    }

    @Override
    public boolean storeMultipleFiles(MultipartFile[] files, String sku) {
        Arrays.stream(files).forEach(file -> {
            try {
                storeFile(file, sku);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return true;
    }

    @Override
    public byte[] getMediaFile(Long fileId) {
        ProductMedia productMedia = productMediaRepository.getById(fileId);
        return ProductMediaCompressor.decompressBytes(productMedia.getData());
    }
}
