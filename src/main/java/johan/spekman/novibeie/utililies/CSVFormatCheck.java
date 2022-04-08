package johan.spekman.novibeie.utililies;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVFormatCheck {
    public static boolean hasCSVFormat(MultipartFile file) {
        String TYPE = "text/csv";
        if (!TYPE.matches(file.getContentType())) {
            return false;
        } else {
            return true;
        }
    }
}
