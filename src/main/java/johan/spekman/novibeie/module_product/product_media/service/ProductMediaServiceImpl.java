package johan.spekman.novibeie.module_product.product_media.service;


import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.module_product.product_media.repository.ProductMediaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import johan.spekman.novibeie.module_product.product_media.model.ProductMedia;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;

@Service
@Transactional
public class ProductMediaServiceImpl implements ProductMediaService {

    private final ProductMediaRepository productMediaRepository;
    private final ProductRepository productRepository;

    public ProductMediaServiceImpl(ProductMediaRepository productMediaRepository, ProductRepository productRepository) {
        this.productMediaRepository = productMediaRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductMedia storeFile(MultipartFile file, String sku) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            if (fileName.contains("..")) {
                throw new IllegalArgumentException("Filename contains invalid path sequence");
            }

            ProductMedia productMedia = new ProductMedia();
            productMedia.setData(file.getBytes());
            productMedia.setFileName(fileName);
            Product product = productRepository.findBySku(sku);
            product.addProductMedia(productMedia);
            return productMediaRepository.save(productMedia);

        } catch (IOException ioException) {
            throw new IllegalArgumentException("Could not store file " + fileName + ". Please try again");
        }
    }

        @Override
        public ProductMedia getFile(Long fileId) {
            return productMediaRepository.findById(fileId)
                    .orElseThrow(() -> new EntityNotFoundException("File not found with id " + fileId));
        }
    }
