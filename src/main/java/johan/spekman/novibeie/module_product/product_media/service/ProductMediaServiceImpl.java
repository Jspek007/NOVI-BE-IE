package johan.spekman.novibeie.module_product.product_media.service;


import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.module_product.product_media.model.ProductMedia;
import johan.spekman.novibeie.module_product.product_media.repository.ProductMediaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class ProductMediaServiceImpl implements ProductMediaService {

    private final ProductMediaRepository productMediaRepository;
    private final ProductRepository productRepository;

    public ProductMediaServiceImpl(ProductMediaRepository productMediaRepository,
                                   ProductRepository productRepository) {
        this.productMediaRepository = productMediaRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void storeFile(MultipartFile file, String sku)
            throws IOException {
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            String contentType = "image/png";
            Product product = productRepository.findBySku(sku);
            if (fileName.contains("..")) {
                throw new ApiRequestException("Invalid file name");
            }
            if (!Objects.equals(file.getContentType(), contentType)) {
                throw new ApiRequestException("Uploaded content type is not supported, please upload: " + contentType);
            }
            if (product == null) {
                throw new ApiRequestException("No product found with sku: " + sku);
            }
            ProductMedia productMedia = new ProductMedia();
            productMedia.setData(ProductMediaCompressor.compressBytes(file.getBytes()));
            productMedia.setFileName(fileName);
            product.addProductMedia(productMedia);
            productMediaRepository.save(productMedia);
        } catch (Exception exception) {
            throw new ApiRequestException("File could not be processed: " + exception.getMessage());
        }
    }

    @Override
    public void storeMultipleFiles(MultipartFile[] files, String sku) {
        Arrays.stream(files).forEach(file -> {
            try {
                storeFile(file, sku);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public byte[] getMediaFile(Long fileId) {
        ProductMedia productMedia = productMediaRepository.getById(fileId);
        return ProductMediaCompressor.decompressBytes(productMedia.getData());
    }

    @Override
    public List<byte[]> getAllMediaBySku(String sku) {
        Long parentId = productRepository.findBySku(sku).getId();
        List<ProductMedia> compressedMediaFiles = productMediaRepository.findByParentId(parentId);

        if (compressedMediaFiles.size() == 0) {
            throw new ApiRequestException("No media files found for this sku: " + sku);
        } else {
            List<byte[]> decompressedMedia = new ArrayList<>();

            compressedMediaFiles.forEach(productMedia ->
                    decompressedMedia.add(ProductMediaCompressor.decompressBytes(productMedia.getData()))
            );
            return decompressedMedia;
        }
    }
}
