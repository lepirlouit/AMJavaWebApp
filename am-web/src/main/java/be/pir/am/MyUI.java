package be.pir.am;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

/**
 *
 */
@Theme("runo")
@Widgetset("be.pir.am.MyAppWidgetset")
public class MyUI extends UI {

	private static final long serialVersionUID = 1L;

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		AthleteService athleteService = ServiceLocator.getInstance().getAthleteService();
		CategoryService categoryService = ServiceLocator.getInstance().getCategoryService();

		Panel panel = new Panel("Inscrption à la competition RRCB - Meeting Express (jeudi 23 avril 2015)");

		final VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		panel.setContent(layout);
		setContent(panel);
		layout.addComponent(new Button("Nouvel Athlete", new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				addWindow(new NewAthleteSubWindows(athleteService) {
					
					@Override
					public void callback(AthleteDto athlete) {
						//athleteService.saveNewAthlete(athlete);
						
						
					}
				});				
			}
		}));
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
		searchBtn.setClickShortcut(KeyCode.ENTER);
		searchBtn.addStyleName(Reindeer.BUTTON_DEFAULT);
		searchForm.addComponents(new FormLayout(bib), new FormLayout(cbxCategory), new FormLayout(searchBtn));
		HorizontalLayout results = new HorizontalLayout();
		Table tb = new Table();
		tb.setVisible(false);
		tb.setContainerDataSource(new BeanItemContainer<AthleteDto>(AthleteDto.class));
		tb.setVisibleColumns("firstName", "lastName", "teamShort");
		tb.setColumnHeader("firstName", "Prénom");
		tb.setColumnHeader("lastName", "Nom");
		tb.setColumnHeader("teamShort", "Club");
		tb.setSelectable(true);
		layout.addComponent(results);
		results.addComponent(tb);
		results.setSpacing(true);
		tb.addValueChangeListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent valueChangeEvent) {
				AthleteDto selectedAthlete = (AthleteDto) tb.getValue();
				if (selectedAthlete != null) {
					CompetitionDto competition = new CompetitionDto();
					competition.setId(71);
					competition.setFederationId(10);
					List<EventDto> eventsList = athleteService.findEventsForAthlete(selectedAthlete, competition);
					VerticalLayout gl = new VerticalLayout();
					gl.addComponent(new Label(selectedAthlete.getFirstName() + ' ' + selectedAthlete.getLastName()
							+ " - " + DateFormat.getDateInstance(SimpleDateFormat.SHORT).format(selectedAthlete.getBirthdate()) + " (" + selectedAthlete.getTeam() + ')'));

					Set<BeanFieldGroup<EventDto>> binders = new HashSet<>();
					for (EventDto event : eventsList) {
						HorizontalLayout eventLyt = new HorizontalLayout();
						eventLyt.setSpacing(true);
						final BeanFieldGroup<EventDto> binder = new BeanFieldGroup<EventDto>(EventDto.class);
						binders.add(binder);
						binder.setBuffered(true);
						binder.setItemDataSource(event);
						Field<?> checkbox = binder.buildAndBind(event.getName(), "checked");

						eventLyt.addComponent(new FormLayout(checkbox));
						if (event.isNeedRecord()) {
							TextField recordField = (TextField) binder.buildAndBind("Record (en secondes)", "record");
							recordField.setNullRepresentation("0,00");
							eventLyt.addComponent(new FormLayout(recordField));
						}
						gl.addComponent(eventLyt);
					}
					Button inscriptionBtn = new Button("Inscription");
					inscriptionBtn.addClickListener(new Button.ClickListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void buttonClick(ClickEvent event) {
							try {
								for (BeanFieldGroup<EventDto> binder : binders) {
									binder.commit();
								}
							} catch (CommitException e) {
								Notification.show("Un erreur s'est produite", "Erreur : " + e.getCause().getMessage(),
										Type.ERROR_MESSAGE);
							}
							athleteService.subscribeAthleteToEvents(selectedAthlete, eventsList,
									(CategoryDto) cbxCategory.getValue(), competition);
							Notification.show("Inscription Ok", Type.HUMANIZED_MESSAGE);
							@SuppressWarnings("unchecked")
							Collection<Property.ValueChangeListener> listeners = (Collection<ValueChangeListener>) tb
									.getListeners(ValueChangeEvent.class);
							for (Property.ValueChangeListener vcl : listeners) {
								vcl.valueChange(valueChangeEvent);
							}

						}
					});
					gl.addComponent(inscriptionBtn);
					results.removeAllComponents();
					results.addComponent(tb);
					results.addComponent(gl);
				}
			}
		});

		searchBtn.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent clevent) {

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

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}
}
