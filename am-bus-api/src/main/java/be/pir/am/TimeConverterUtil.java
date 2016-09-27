package be.pir.am;

import java.math.BigDecimal;

/**
 * Created by Benoit on 25-09-16.
 */
public class TimeConverterUtil {
    public static String convertRecordToString(BigDecimal value) {
        StringBuilder builder = new StringBuilder(3 + 3 + 2);
        BigDecimal[] mins = value.divideAndRemainder(BigDecimal.valueOf(60L));
        if (mins[0].compareTo(BigDecimal.ZERO) > 0)
            builder.append(mins[0].intValue()).append('\'');
        BigDecimal sec = mins[1];
        if (mins[0].compareTo(BigDecimal.ZERO) > 0 && sec.compareTo(BigDecimal.valueOf(10L)) < 0)
            builder.append('0');
        BigDecimal[] secs = sec.divideAndRemainder(BigDecimal.valueOf(1L));
        builder.append(secs[0].intValue()).append('"');
        int cent = secs[1].multiply(BigDecimal.valueOf(100L)).intValue();
        if (cent < 10)
            builder.append('0');
        builder.append(cent);
        return builder.toString();
    }
}
