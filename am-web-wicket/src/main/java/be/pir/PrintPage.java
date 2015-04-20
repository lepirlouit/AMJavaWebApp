package be.pir;

import java.util.List;

import javax.ejb.EJB;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CompetitionDto;
import be.pir.am.api.dto.EventDto;
import be.pir.am.api.service.AthleteService;

public class PrintPage extends WebPage {

	private static final long serialVersionUID = 1L;
	@EJB
	private AthleteService athleteService;

	public PrintPage(final PageParameters parameters) {
		super(parameters);
		CompetitionDto competition = new CompetitionDto();
		competition.setId(71);
		List<EventDto> events = athleteService.getAllParticipations(competition);
		add(new PropertyListView<EventDto>("event", events) {

			@Override
			protected void populateItem(ListItem<EventDto> item) {
				item.add(new Label("number"));
				item.add(new Label("hour"));
				item.add(new Label("name"));
				final int subscribed = item.getModelObject().getParticipants().size();
				item.add(new PropertyListView<AthleteDto>("athlete", item.getModelObject().getParticipants()) {

					@Override
					protected void populateItem(ListItem<AthleteDto> athleteItem) {
						athleteItem.add(new Label("bib"));
						athleteItem.add(new Label("displayName"));
						athleteItem.add(new Label("teamShort"));
						athleteItem.add(new Label("birthdate"));
						athleteItem.add(new Label("category"));
					}
				});
				RepeatingView emptyLines = new RepeatingView("emptyLines");
				for(int i=0 ;i<(40-subscribed);i++){
					emptyLines.add(new Label(emptyLines.newChildId(),"<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>").setEscapeModelStrings(false));
				}
				item.add(emptyLines);
			}
		});
	}
}
