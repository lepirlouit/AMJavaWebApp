package be.pir.am;

import java.awt.Checkbox;
import java.util.Arrays;
import java.util.List;

import javax.servlet.annotation.WebServlet;

import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CategoryDto;
import be.pir.am.api.dto.CompetitionDto;
import be.pir.am.api.dto.EventDto;
import be.pir.am.api.service.AthleteService;
import be.pir.am.api.service.CategoryService;
import be.pir.am.util.ServiceLocator;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *
 */
@Theme("mytheme")
@Widgetset("be.pir.am.MyAppWidgetset")
public class MyUI extends UI {

	private static final long serialVersionUID = 1L;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		AthleteService athleteService = ServiceLocator.getInstance().getAthleteService();
		CategoryService categoryService = ServiceLocator.getInstance().getCategoryService();

		final VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		setContent(layout);
		final HorizontalLayout searchForm = new HorizontalLayout();
		searchForm.setSpacing(true);
		layout.addComponent(searchForm);

		ObjectProperty<Integer> bibProperty = new ObjectProperty<Integer>(null, Integer.class);
		TextField bib = new TextField("Dossard/Bib", bibProperty);
		bib.setNullRepresentation("");
		ComboBox cbxCategory = new ComboBox("Category");

		cbxCategory.setContainerDataSource(new BeanItemContainer<CategoryDto>(CategoryDto.class, categoryService
				.getCategoriesForLbfa()));
		cbxCategory.setItemCaptionPropertyId("name");

		Button searchBtn = new Button("Search");
		searchForm.addComponents(new FormLayout(bib), new FormLayout(cbxCategory), new FormLayout(searchBtn));

		Table tb = new Table();
		tb.setVisible(false);
		tb.setContainerDataSource(new BeanItemContainer<AthleteDto>(AthleteDto.class));
		tb.setVisibleColumns("firstName", "lastName");
		tb.setSelectable(true);
		layout.addComponent(tb);
		tb.addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent valueChangeEvent) {
				AthleteDto selectedAthlete = (AthleteDto) tb.getValue();
				
				CompetitionDto competition = new CompetitionDto();
				competition.setId(71);
				competition.setFederationId(10);
				List<EventDto> eventsList = athleteService.findEventsForAthlete(selectedAthlete,competition);
				GridLayout gl = new GridLayout(2,eventsList.size());
				int i=0;
				for(EventDto event : eventsList){
					gl.addComponent(new CheckBox(event.getName(),event.isChecked()),0,i);
					gl.addComponent(new TextField("",""+event.getRecord()),1,i);
					i++;
				}
			}
		});

		searchBtn.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent clevent) {
				AthleteDto athlete = new AthleteDto();
				athlete.setId(1026657);//sarah
				CompetitionDto competition = new CompetitionDto();
				competition.setId(71);
				competition.setFederationId(10);
				EventDto event = new EventDto();
				event.setId(3);
				CategoryDto category = new CategoryDto();
				category.setId(58);

				athleteService.subscribeAthleteToEvents(athlete, Arrays.asList(event), category, competition);

				List<AthleteDto> athls = athleteService.findAthletesByBibAndCategory(bibProperty.getValue(),
						(CategoryDto) cbxCategory.getValue());
				boolean foundAth = athls.size() > 0;
				if (!foundAth) {
					Notification.show("Not Found"); // TODO : atheletes non
													// trouve, create new.
				}
				tb.setVisible(foundAth);
				tb.removeAllItems();
				tb.addItems(athls);
			}
		});

	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
