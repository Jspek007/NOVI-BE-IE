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

    public ProductMediaServiceImpl(ProductMediaRepository productMediaRepository,
                                   ProductRepository productRepository)  {
        this.productMediaRepository = productMediaRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductMedia storeFile(MultipartFile file, String sku) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (fileName.contains("..")) {
            throw new IllegalArgumentException("Filename contains invalid path sequence");
        }
        ProductMedia productMedia = new ProductMedia();
        productMedia.setData(ProductMediaCompressor.compressBytes(file.getBytes()));
        productMedia.setFileName(fileName);
        Product product = productRepository.findBySku(sku);
        product.addProductMedia(productMedia);
        return productMediaRepository.save(productMedia);

    }

        @Override
        public ProductMedia getFile(String sku) {
        Long parentId = productRepository.findBySku(sku).getId();
            return productMediaRepository.findByParentId(parentId);
        }
    }
