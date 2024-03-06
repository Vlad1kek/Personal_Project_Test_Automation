package utils.run;

import java.time.LocalDate;

public class TestUtils {

    public static String getMonthYear() {
        LocalDate currentDate = LocalDate.now();

        String month = String.valueOf(currentDate.getMonth());
        month = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();

        String year = Integer.toString(currentDate.getYear());

        return month + " " + year;
    }
}
