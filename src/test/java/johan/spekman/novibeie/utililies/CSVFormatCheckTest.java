package johan.spekman.novibeie.utililies;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CSVFormatCheckTest {
    @MockBean
    CSVFormatCheck csvFormatCheck;

    @Test
    public void wrongFileFormatShouldReturnFalse() {
        MockMultipartFile wrongFileFormat = new MockMultipartFile("data", "filename.txt", "text/plain",
                "some xml".getBytes());

        assertFalse(CSVFormatCheck.hasCSVFormat(wrongFileFormat));
    }

    @Test
    public void csvFileFormatShouldReturnTrue() {
    MockMultipartFile mockMultipartFile = new MockMultipartFile("data", "dummy.csv", "text/csv",
            "some dataset".getBytes());
    assertTrue(CSVFormatCheck.hasCSVFormat(mockMultipartFile));
    }
}