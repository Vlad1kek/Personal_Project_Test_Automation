package utils.run;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class TestUtils {
    private static Calendar calendar;
    private static SimpleDateFormat sdf ;
    private static LocalDate currentDate;

    public static String getMonthYear() {
        currentDate = LocalDate.now();
        String month = String.valueOf(currentDate.getMonth());
        month = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();

        String year = Integer.toString(currentDate.getYear());

        return month + " " + year;
    }

    public static String getMonthAheadDate() {
        calendar = Calendar.getInstance();
        sdf = new SimpleDateFormat("MMM.dd.yyyy");
        calendar.add(Calendar.MONTH, 1);

        return sdf.format(calendar.getTime());
    }

    public static String getCurrentDate() {
        currentDate = LocalDate.now();
        String month = String.valueOf(currentDate.getMonth());
        month = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();

        String date = String.valueOf(currentDate.getDayOfMonth());
        String year = Integer.toString(currentDate.getYear());

        return month + " " + date + "th, " + year;
    }
}
