package johan.spekman.novibeie.utililies;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class CSVFormatCheck {
    public static boolean hasCSVFormat(MultipartFile file) {
        String TYPE = "text/csv";
        return TYPE.matches(Objects.requireNonNull(file.getContentType()));
    }
}
