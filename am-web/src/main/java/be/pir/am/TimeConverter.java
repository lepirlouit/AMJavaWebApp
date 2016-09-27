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
		throw new ConversionException("[" + value + "] n'est pas valide. Format : 0'00\"00 (min'secondes\"centièmes)");
	}

	@Override
	public String convertToPresentation(BigDecimal value, Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if (value == null)
			return null;
		return TimeConverterUtil.convertRecordToString(value);
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
