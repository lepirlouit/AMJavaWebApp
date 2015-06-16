package be.pir.am;

import java.util.List;
import java.util.Locale;

import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.TeamDto;
import be.pir.am.api.service.AthleteService;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public abstract class NewAthleteSubWindows extends Window{
	private static final long serialVersionUID = 1L;
	
	private AthleteService athleteService;

	public NewAthleteSubWindows(AthleteService athleteService) {
        super("Inscription Nouvel Athlète"); // Set window caption
        this.athleteService=athleteService;
        center();

        AthleteDto athlete = new AthleteDto();
        
        
        final BeanFieldGroup<AthleteDto> binder =
                new BeanFieldGroup<AthleteDto>(AthleteDto.class);
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
        content.addComponent(binder.buildAndBind("Date de Naissance", "birthdate"));
        
		List<TeamDto> listAllTeams = athleteService.listAllTeams();
		ComboBox cbxTeam = new ComboBox("Club", new BeanItemContainer<TeamDto>(TeamDto.class,listAllTeams));
		cbxTeam.setItemCaptionPropertyId("name");
		cbxTeam.setConverter(new Converter<Object,String>(){

			private static final long serialVersionUID = 1L;

			@Override
			public String convertToModel(Object value,
					Class<? extends String> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException {
				TeamDto team=(TeamDto)value;
				if(team!=null) return team.getName();
				return null;
			}

			@Override
			public Object convertToPresentation(String value,
					Class<? extends Object> targetType, Locale locale)
					throws com.vaadin.data.util.converter.Converter.ConversionException {
				for (TeamDto teamDto : listAllTeams) {
					if (teamDto.getName().equals(value)) return teamDto; 
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
        
        TextField dossardField = (TextField) binder.buildAndBind("Dossard", "bib");
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

			public void buttonClick(ClickEvent event) {
                close(); // Close the sub-window
                callback(athlete);
            }
        });
        content.addComponent(ok);
	}
	
	public abstract void callback(AthleteDto athlete);

}
