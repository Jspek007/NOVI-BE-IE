package johan.spekman.novibeie.module_product.ProductMediaTests.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.module_product.product_media.model.ProductMedia;
import johan.spekman.novibeie.module_product.product_media.repository.ProductMediaRepository;
import johan.spekman.novibeie.module_product.product_media.service.ProductMediaServiceImpl;
import johan.spekman.novibeie.utililies.InputValidation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductMediaServiceTest {
    @Mock
    private ProductMediaRepository productMediaRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMedia productMedia;
    @Mock
    private Product product;
    @Mock
    InputValidation inputValidation;
    @Mock
    BindingResult bindingResult;

    @MockBean
    AutoCloseable autoCloseable;

    @InjectMocks
    ProductMediaServiceImpl underTest;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new ProductMediaServiceImpl(productMediaRepository, productRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldAddNewFileToProduct() throws IOException {
        MockMultipartFile file = new MockMultipartFile(
                "image1",
                "image1.png",
                "image/png",
                "image".getBytes());
        Product product = new Product();
        product.setSku("sku_123456");
        when(productRepository.findBySku(anyString())).thenReturn(product);

        underTest.storeFile(file, product.getSku());

        Product capturedProduct = productRepository.findBySku("sku_123456");
        assertThat(capturedProduct.getProductMediaList().get(0).getFileName()).isEqualTo(file.getOriginalFilename());
    }

    @Test
    void invalidFileName_shouldReturnException() {
        MockMultipartFile file = new MockMultipartFile(
                "image1",
                "image1..png",
                "image/png",
                "image".getBytes());

        String expectedException = "File could not be processed: Invalid file name";
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> underTest.storeFile(file,
                "sku_123456"));
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    void invalidContentType_shouldThrowException() {
        MockMultipartFile file = new MockMultipartFile(
                "image1",
                "image1.png",
                "image/jpg",
                "image".getBytes());

        String expectedException = "File could not be processed: Uploaded content type is not supported, please " +
                "upload: image/png";
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> underTest.storeFile(file,
                "sku_123456"));
        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    void noProductFound_shouldThrowException() {
        MockMultipartFile file = new MockMultipartFile(
                "image1",
                "image1.png",
                "image/png",
                "image".getBytes());

        String expectedException = "File could not be processed: No product found with sku: sku_123456";

        when(productRepository.findBySku(anyString())).thenReturn(null);
        ApiRequestException exception = assertThrows(ApiRequestException.class, () -> underTest.storeFile(file,
                "sku_123456"));

        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    void storeMultipleFiles() {
        MockMultipartFile[] files = {
                new MockMultipartFile(
                        "image1",
                        "image1.png",
                        "image/png",
                        "image".getBytes()),
                new MockMultipartFile(
                        "image2",
                        "image2.png",
                        "image/png",
                        "image".getBytes()),
        };
        Product product = new Product();
        product.setSku("sku_123456");
        when(productRepository.findBySku(anyString())).thenReturn(product);
        underTest.storeMultipleFiles(files, "sku_123456");

        Product capturedProduct = productRepository.findBySku("sku_123456");

        assertThat(capturedProduct.getProductMediaList().size()).isEqualTo(files.length);

    }

    @Test
    void shouldReturnMediaFileForGivenId() {
        ProductMedia productMedia = new ProductMedia(
                product,
                1L,
                "test image",
                "image".getBytes()
        );

        when(productMediaRepository.getById(anyLong())).thenReturn(productMedia);
        underTest.getMediaFile(1L);

        assertThat(productMedia.getFileName()).isEqualTo("test image");
    }

    @Test
    void shouldReturnBytesForAllMediaBySku() {
        List<ProductMedia> productMediaFile = new ArrayList<>();
        ProductMedia productMedia = new ProductMedia(
                product,
                1L,
                "test image",
                "image".getBytes()
        );
        ProductMedia productMedia1 = new ProductMedia(
                product,
                2L,
                "test image 2",
                "image2".getBytes()
        );
        Product product = new Product();
        productMedia.setProduct(product);
        productMediaFile.add(productMedia);
        productMediaFile.add(productMedia1);
        when(productRepository.findBySku(anyString())).thenReturn(product);
        when(productMediaRepository.findByParentId(null)).thenReturn(productMediaFile);

        List<byte[]> mediaFiles =  underTest.getAllMediaBySku("sku_123456");
        assertThat(mediaFiles.size()).isEqualTo(productMediaFile.size());
    }
}
