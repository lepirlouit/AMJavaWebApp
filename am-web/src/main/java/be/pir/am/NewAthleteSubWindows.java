package be.pir.am;

import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;

import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.TeamDto;
import be.pir.am.api.service.AthleteService;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public abstract class NewAthleteSubWindows extends Window {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(NewAthleteSubWindows.class);

	public NewAthleteSubWindows(AthleteService athleteService) {
		super("Inscription Nouvel Athlète"); // Set window caption
		center();

		AthleteDto athlete = new AthleteDto();

		final BeanFieldGroup<AthleteDto> binder = new BeanFieldGroup<>(AthleteDto.class);
		binder.setItemDataSource(athlete);
		binder.setBuffered(true);

		// Some basic content for the window
		VerticalLayout content = new VerticalLayout();

		TextField firstNameField = (TextField) binder.buildAndBind("Prénom", "firstName");
		firstNameField.setNullRepresentation("");
		content.addComponent(firstNameField);

		TextField nameField = (TextField) binder.buildAndBind("Nom", "lastName");
		nameField.setNullRepresentation("");
		content.addComponent(nameField);

		OptionGroup genderOptiongroup = new OptionGroup("Gender");
		genderOptiongroup.addItem('M');
		genderOptiongroup.setItemCaption('M', "Male");
		genderOptiongroup.addItem('W');
		genderOptiongroup.setItemCaption('W', "Female");
		binder.bind(genderOptiongroup, "gender");
		content.addComponent(genderOptiongroup);

		DateField birthdateField = (DateField) binder.buildAndBind("Date de Naissance", "birthdate");
		birthdateField.setDateFormat("dd-MM-yyyy");
		content.addComponent(birthdateField);

		List<TeamDto> listAllTeams = athleteService.listAllTeams();
		ComboBox cbxTeam = new ComboBox("Club", new BeanItemContainer<>(TeamDto.class, listAllTeams));
		cbxTeam.setItemCaptionPropertyId("name");
		cbxTeam.setConverter(new Converter<Object, String>() {

			private static final long serialVersionUID = 1L;

			@Override
			public String convertToModel(Object value, Class<? extends String> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException {
				TeamDto team = (TeamDto) value;
				if (team != null) {
					return team.getName();
				}
				return null;
			}

			@Override
			public Object convertToPresentation(String value, Class<?> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException {
				for (TeamDto teamDto : listAllTeams) {
					if (teamDto.getName().equals(value)) {
						return teamDto;
					}
				}
				return null;
			}

			@Override
			public Class<String> getModelType() {
				return String.class;
			}

			@Override
			public Class<Object> getPresentationType() {
				return Object.class;
			}

		});
		binder.bind(cbxTeam, "team");
		content.addComponent(cbxTeam);

		TextField dossardField = (TextField) binder.buildAndBind("Dossard (facultatif)", "bib");
		dossardField.setNullRepresentation("");
		content.addComponent(dossardField);
		content.setMargin(true);
		setContent(content);

		// Disable the close button
		setClosable(false);

		// Trivial logic for closing the sub-window
		Button ok = new Button("OK");
		ok.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					binder.commit();
					close(); // Close the sub-window
					callback(athlete);
				} catch (CommitException e) {
					LOGGER.info("error occured for user : " + e.getCause().getMessage());
					Notification.show("Un erreur s'est produite", "Erreur : " + e.getCause().getMessage(),
							Type.ERROR_MESSAGE);
				}
			}
		});

		Button cancel = new Button("Cancel");
		cancel.addClickListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
		content.addComponent(new HorizontalLayout(ok, cancel));
	}

	public abstract void callback(AthleteDto athlete);

}
