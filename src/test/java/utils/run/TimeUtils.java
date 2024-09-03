package utils.run;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class TimeUtils {

    public static String getMonthYear() {
        LocalDate currentDate = LocalDate.now();
        String month = String.valueOf(currentDate.getMonth());
        month = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();

        String year = Integer.toString(currentDate.getYear());

        return month + " " + year;
    }

    private static String getDayOfMonthSuffix(final int n) {
        if (n < 1 || n > 31) return "Invalid date";
        if (n >= 11 && n <= 13) return "th";

        return switch (n % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }

    private static String getDate(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM", new Locale("en"));
        SimpleDateFormat dayFormat = new SimpleDateFormat("d", new Locale("en"));
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", new Locale("en"));

        return sdf.format(calendar.getTime())
                + " "
                + dayFormat.format(calendar.getTime())
                + getDayOfMonthSuffix(calendar.get(Calendar.DAY_OF_MONTH))
                + ", "
                + yearFormat.format(calendar.getTime());
    }

    public static List<String> getCurrentDateList() {
        return List.of(getDate(Calendar.getInstance()));
    }

    public static List<String> getWeeklyDatesList() {
        Calendar calendar = Calendar.getInstance();
        List<String> dates = new ArrayList<>();

        int currentMonth = calendar.get(Calendar.MONTH);
        while (calendar.get(Calendar.MONTH) == currentMonth) {
            dates.add(getDate(calendar));
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
        }

        return Collections.singletonList(String.join("\n", dates));
    }

    public static List<String> getDailyDatesList() {
        Calendar calendar = Calendar.getInstance();
        List<String> dates = new ArrayList<>();

        int currentMonth = calendar.get(Calendar.MONTH);
        while (calendar.get(Calendar.MONTH) == currentMonth && dates.size() < 13) {
            dates.add(getDate(calendar));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return Collections.singletonList(String.join("\n", dates));
    }

    public static String getExpectedMonthlyCostsYearly(String min, String max) {
        double low = Integer.parseInt(min);
        double high = Integer.parseInt(max);
        double sum;
        sum = (low + high) / 2 / 12;

        return "€" + String.format(Locale.ROOT, "%.2f", sum);
    }

    public static String getExpectedMonthlyCostsHalfYear(String min, String max) {
        double low = Integer.parseInt(min);
        double high = Integer.parseInt(max);
        double sum;
        sum = (low + high) / 2 / 6;

        return "€" + String.format(Locale.ROOT, "%.2f", sum);
    }
}
