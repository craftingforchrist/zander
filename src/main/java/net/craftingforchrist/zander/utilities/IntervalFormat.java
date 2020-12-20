package net.craftingforchrist.zander.utilities;

import java.util.Calendar;

public class IntervalFormat {
    public static String format(long seconds) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(seconds);

            int year = calendar.get(Calendar.YEAR)-1970;
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH)-1;
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);

            return year + " years, " + month + " months, " + day + " days, " + hour + " hours, " + minute + " minutes and " + second + " seconds.";
        } catch (Exception e) {
            // In case joda can't do it.
            return "a long time (" + ( seconds / 1000 / 60 / 60 / 24 ) + " days ago)";
        }
    }
}
