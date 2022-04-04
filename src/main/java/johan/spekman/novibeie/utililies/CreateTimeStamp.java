package johan.spekman.novibeie.utililies;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CreateTimeStamp {

    public Date CreateTimeStamp() throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date((System.currentTimeMillis()));
        String currentDate = format.format(date);
        Date createdAtDate = format.parse(currentDate);
        return createdAtDate;
    }
}
