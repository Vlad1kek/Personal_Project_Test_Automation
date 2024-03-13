package utils.run;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TestUtils {
    private static LocalDate currentDate;

    public static String getMonthYear() {
        currentDate = LocalDate.now();
        String month = String.valueOf(currentDate.getMonth());
        month = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();

        String year = Integer.toString(currentDate.getYear());

        return month + " " + year;
    }

    public static List<String> getWeeklyDates() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd', 'yyyy", new Locale("en"));

        List<String> dates = new ArrayList<>();
        int currentMonth = calendar.get(Calendar.MONTH);
        while (calendar.get(Calendar.MONTH) == currentMonth) {
            dates.add(sdf.format(calendar.getTime()) + getDayOfMonthSuffix(calendar.get(Calendar.DAY_OF_MONTH)));
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }
        return dates;
    }

    public static String getDayOfMonthSuffix(final int n) {
        if (n < 1 || n > 31) return "Invalid date";
        if (n >= 11 && n <= 13) return "th";
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
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
