package com.td.wallendar.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateFormatter {

    final static Map<Integer, String> monthNumberToMonth = new HashMap<>() {{
        put(0, "Ene");
        put(1, "Feb");
        put(2, "Mar");
        put(3, "Abr");
        put(4, "May");
        put(5, "Jun");
        put(6, "Jul");
        put(7, "Ago");
        put(8, "Sep");
        put(9, "Oct");
        put(10, "Nov");
        put(11, "Dic");
    }};

    final static Map<Integer, String> dayNumberToDay = new HashMap<>() {{
        put(1, "Lun");
        put(2, "Mar");
        put(3, "Mier");
        put(4, "Jue");
        put(5, "Vier");
        put(6, "Sab");
        put(7, "Dom");
    }};


    public static String formatDateAsString(final Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());

        return String.format("%s - %s %s - %s:%s",
                dayNumberToDay.get(calendar.get(Calendar.DAY_OF_WEEK)),
                monthNumberToMonth.get(calendar.get(Calendar.MONTH)),
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE)
        );
    }
}
