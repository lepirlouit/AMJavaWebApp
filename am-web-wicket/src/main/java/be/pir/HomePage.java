package be.pir;

import javax.ejb.EJB;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import be.pir.am.api.service.AthleteService;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;

	@EJB
	private AthleteService athleteService;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		add(new Label("version", getApplication().getFrameworkSettings().getVersion()));
		athleteService.findAthletesByBib(1997);
		// TODO Add your page's components here

    }
}
