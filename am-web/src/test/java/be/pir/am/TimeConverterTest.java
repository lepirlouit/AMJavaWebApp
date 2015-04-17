package be.pir.am;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class TimeConverterTest {
	@Test
	public void testConvertToModel() {
		TimeConverter timeConverter = new TimeConverter();
		BigDecimal convertToModel = timeConverter.convertToModel("1'04\"24", null, null);
		Assert.assertEquals(BigDecimal.valueOf(64.24), convertToModel);
		convertToModel = timeConverter.convertToModel("64\"24", null, null);
		Assert.assertEquals(BigDecimal.valueOf(64.24), convertToModel);
		convertToModel = timeConverter.convertToModel("4\"24", null, null);
		Assert.assertEquals(BigDecimal.valueOf(4.24), convertToModel);
		convertToModel = timeConverter.convertToModel("29\"05", null, null);
		Assert.assertEquals(BigDecimal.valueOf(29.05), convertToModel);
		convertToModel = timeConverter.convertToModel("29\"5", null, null);
		Assert.assertEquals(BigDecimal.valueOf(29.05), convertToModel);
		convertToModel = timeConverter.convertToModel("3'05\"5", null, null);
		Assert.assertEquals(BigDecimal.valueOf(185.05), convertToModel);
		convertToModel = timeConverter.convertToModel("3'5\"24", null, null);
		Assert.assertEquals(BigDecimal.valueOf(185.24), convertToModel);
		convertToModel = timeConverter.convertToModel("3'5\"5", null, null);
		Assert.assertEquals(BigDecimal.valueOf(185.05), convertToModel);
	}

	@Test
	public void testConvertToPresentation() {
		TimeConverter timeConverter = new TimeConverter();
		String presentation = timeConverter.convertToPresentation(BigDecimal.valueOf(64.24), null, null);
		Assert.assertEquals("1'04\"24", presentation);
		presentation = timeConverter.convertToPresentation(BigDecimal.valueOf(4.24), null, null);
		Assert.assertEquals("4\"24", presentation);
		presentation = timeConverter.convertToPresentation(BigDecimal.valueOf(9.05), null, null);
		Assert.assertEquals("9\"05", presentation);
		presentation = timeConverter.convertToPresentation(BigDecimal.valueOf(185.05), null, null);
		Assert.assertEquals("3'05\"05", presentation);
		presentation = timeConverter.convertToPresentation(BigDecimal.valueOf(185.24), null, null);
		Assert.assertEquals("3'05\"24", presentation);

	}
}
