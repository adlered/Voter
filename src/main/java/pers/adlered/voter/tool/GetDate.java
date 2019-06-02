package pers.adlered.voter.tool;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDate {
    public static Integer year() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return Integer.parseInt(simpleDateFormat.format(date));
    }
}
