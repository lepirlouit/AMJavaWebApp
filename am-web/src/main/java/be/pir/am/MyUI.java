package be.pir.am;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.service.AthleteService;
import be.pir.am.util.ServiceLocator;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
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

	private AthleteService athleteService = ServiceLocator.getInstance()
			.getAthleteService();

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		ObjectProperty<Integer> bibProperty = new ObjectProperty<Integer>(0,
				Integer.class);
		TextField tf = new TextField("bib", bibProperty);
		layout.addComponent(tf);

		Table tb = new Table();
		tb.addContainerProperty("firstName", String.class, "");
		tb.addContainerProperty("lastName", String.class, "");
		tb.setContainerDataSource(new BeanItemContainer<AthleteDto>(
				AthleteDto.class));
		layout.addComponent(tb);

		Button button = new Button("Click Me");
		button.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking"));
				List<AthleteDto> athls = athleteService
						.findAthletesByBib(bibProperty.getValue());
				tb.removeAllItems();
				tb.addItems(athls);
			}
		});
		layout.addComponent(button);

	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
