package be.pir;

import javax.ejb.EJB;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import be.pir.am.api.service.AthleteService;

public class PrintPage extends WebPage {

	private static final long serialVersionUID = 1L;
	@EJB
	private AthleteService athleteService;

	public PrintPage(final PageParameters parameters) {
		super(parameters);
	}
}
