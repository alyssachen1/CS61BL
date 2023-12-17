import java.io.*;

public class DateConverter {
    /**
     * Given a day number in 988, an integer between 1 and 365, as a
     * command-line argument, prints the date in month/day format.
     * <p>
     * java DateConverter 365
     * <p>
     * should print 12/31
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please provide a command-line argument representing the day of the year.");
            return;
        }

        int dayOfYear = 0;
        try {
            dayOfYear = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        int month = 1;
        int dateInMonth;
        int daysInMonth;

        while (dayOfYear > 0) {
            if (month == 2) {
                daysInMonth = 28;
            } else if (month == 4 || month == 6 || month == 9 || month == 11) {
                daysInMonth = 30;
            } else {
                daysInMonth = 31;
            }

            if (dayOfYear > daysInMonth) {
                dayOfYear = dayOfYear - daysInMonth;
                month++;
            } else {
                break;
            }
        }

        dateInMonth = dayOfYear;
        System.out.println(month + "/" + dateInMonth);
    }
}