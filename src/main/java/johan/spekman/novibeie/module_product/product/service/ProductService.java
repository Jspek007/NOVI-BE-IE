package johan.spekman.novibeie.module_product.product.service;

import johan.spekman.novibeie.module_product.product.dto.ProductDto;
import johan.spekman.novibeie.module_product.product.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.InputStream;
import java.io.Writer;
import java.text.ParseException;
import java.util.List;

public interface ProductService {
    void createProduct(@Valid ProductDto productDto, BindingResult bindingResult) throws ParseException;
    List<Product> getAllProducts();
    Product getProductBySku(String sku);
    void deleteProductBySku(String sku);
    void updateProduct(String sku, ProductDto productDto, BindingResult bindingResult);
    void productExport(Writer writer);
    List<Product> productImport(InputStream inputStream);
    void saveAll(MultipartFile file);
}
