package be.pir.am;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.data.util.converter.Converter;

public class TimeConverter implements Converter<String, BigDecimal> {

	private static final long serialVersionUID = 1L;

	@Override
	public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (value == null)
			return null;
		Pattern pattern = Pattern.compile("^(?:(?<min>(?:\\d)?\\d)')?(?<sec>(?:\\d)?\\d)\"(?<cent>\\d(?:\\d)?)$");
		Matcher matcher = pattern.matcher(value);
		if (matcher.matches()) {
			BigDecimal timeInSeconds = BigDecimal.ZERO;
			String minString = matcher.group("min");
			if (minString != null) {
				timeInSeconds = timeInSeconds.add(BigDecimal.valueOf(Long.valueOf(minString) * 60));
			}
			String secString = matcher.group("sec");
			if (secString != null) {
				timeInSeconds = timeInSeconds.add(BigDecimal.valueOf(Long.valueOf(secString)));
			}
			String centString = matcher.group("cent");
			if (centString != null) {
				timeInSeconds = timeInSeconds.add(BigDecimal.valueOf(Long.valueOf(centString) / 100.));
			}
			return timeInSeconds;
		}
		throw new ConversionException("Cannot be converted {0}");
	}

	@Override
	public String convertToPresentation(BigDecimal value, Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (value == null)
			return null;
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

	@Override
	public Class<BigDecimal> getModelType() {
		return BigDecimal.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}
