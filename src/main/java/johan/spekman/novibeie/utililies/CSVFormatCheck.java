package johan.spekman.novibeie.utililies;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVFormatCheck {
    public boolean hasCSVFormat(MultipartFile file) {
        String TYPE = "text/csv";
        return !TYPE.equals(file.getContentType());
    }
}
