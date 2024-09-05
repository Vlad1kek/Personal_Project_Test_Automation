package utils.run;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Utility class for handling date and time-related operations.
 * <p>
 * The `TimeUtils` class provides methods for:
 * <ul>
 *      <li>Retrieving formatted date strings like the current month and year.</li>
 *      <li>Generating lists of dates for weekly and daily intervals within the current month.</li>
 *      <li>Calculating expected monthly costs based on provided minimum and maximum values, either yearly or for half a year.</li>
 *</ul>
 * This class utilizes Java's `LocalDate`, `Calendar`, and `SimpleDateFormat` to format and manipulate dates,
 * and provides convenient methods for retrieving date lists for various purposes.
 *
 * <p><strong>Usage Examples:</strong></p>
 * <ul>
 *      <li>To get the current month and year: <code>TimeUtils.getMonthYear();</code></li>
 *      <li>To get a list of weekly dates in the current month: <code>TimeUtils.getWeeklyDatesList();</code></li>
 *      <li>To calculate the expected monthly costs: <p><code>TimeUtils.getExpectedMonthlyCostsYearly("1000", "2000");</code></li>
 * </ul>
 */
public class TimeUtils {

    /**
     * Returns the current month and year in a formatted string.
     * The month is capitalized, and the format is: "Month Year" (e.g., "September 2024").
     *
     * @return a string representing the current month and year.
     */
    public static String getMonthYear() {
        LocalDate currentDate = LocalDate.now();
        String month = String.valueOf(currentDate.getMonth());
        month = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase();

        String year = Integer.toString(currentDate.getYear());

        return month + " " + year;
    }

    /**
     * Returns a formatted date string with the correct suffix for the day (e.g., "1st", "2nd", "3rd").
     * <p>The format is: "Month DaySuffix, Year" (e.g., "September 1st, 2024").
     *
     * @param calendar the calendar instance to format the date from.
     * @return a formatted date string.
     */
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

    /**
     * Returns a list containing the current date in the format: "Month DaySuffix, Year".
     *
     * @return a list containing the current date string.
     */
    public static List<String> getCurrentDateList() {
        return List.of(getDate(Calendar.getInstance()));
    }

    /**
     * Returns a list of dates representing weekly intervals within the current month.
     *
     * @return a list of weekly date strings for the current month.
     */
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

    /**
     * Returns a list of daily dates for the current month, limited to the first 13 days.
     *
     * @return a list of daily date strings.
     */
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

    /**
     * Calculates the expected monthly costs based on provided minimum and maximum values, averaged over a year.
     *
     * @param min the minimum cost as a string.
     * @param max the maximum cost as a string.
     * @return the expected monthly cost as a formatted string.
     */
    public static String getExpectedMonthlyCostsYearly(String min, String max) {
        double low = Integer.parseInt(min);
        double high = Integer.parseInt(max);
        double sum = (low + high) / 2 / 12;

        return "€" + String.format(Locale.ROOT, "%.2f", sum);
    }

    /**
     * Calculates the expected monthly costs based on provided minimum and maximum values, averaged over half a year.
     *
     * @param min the minimum cost as a string.
     * @param max the maximum cost as a string.
     * @return the expected monthly cost for half a year as a formatted string.
     */
    public static String getExpectedMonthlyCostsHalfYear(String min, String max) {
        double low = Integer.parseInt(min);
        double high = Integer.parseInt(max);
        double sum = (low + high) / 2 / 6;

        return "€" + String.format(Locale.ROOT, "%.2f", sum);
    }

    /**
     * Determines the appropriate suffix for a day of the month (e.g., "1st", "2nd", "3rd").
     *
     * @param n the day of the month as an integer.
     * @return the suffix for the day.
     */
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
}
