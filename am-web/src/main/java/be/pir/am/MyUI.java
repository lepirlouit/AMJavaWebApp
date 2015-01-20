package be.pir.am;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import be.pir.am.api.dto.AthleteDto;
import be.pir.am.api.dto.CategoryDto;
import be.pir.am.api.service.AthleteService;
import be.pir.am.api.service.CategoryService;
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
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
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
		AthleteService athleteService = ServiceLocator.getInstance()
				.getAthleteService();
		CategoryService categoryService = ServiceLocator.getInstance().getCategoryService();

		final VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(true);
		layout.setMargin(true);
		setContent(layout);
		final HorizontalLayout searchForm = new HorizontalLayout();
		searchForm.setSpacing(true);
		layout.addComponent(searchForm);
		
		ObjectProperty<Integer> bibProperty = new ObjectProperty<Integer>(null, Integer.class);
		TextField bib = new TextField("Dossard/Bib",bibProperty);
		bib.setNullRepresentation("");
		ComboBox category = new ComboBox("Category");

		category.setContainerDataSource(new BeanItemContainer<CategoryDto>(CategoryDto.class, categoryService
				.getCategoriesForLbfa()));
		category.setItemCaptionPropertyId("name");

		Button searchBtn = new Button("Search");
		searchForm.addComponents(new FormLayout(bib), new FormLayout(category), new FormLayout(searchBtn));



		Table tb = new Table();
		tb.setVisible(false);
		tb.setContainerDataSource(new BeanItemContainer<AthleteDto>(
				AthleteDto.class));
		tb.setVisibleColumns("firstName", "lastName");
		layout.addComponent(tb);

		searchBtn.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				List<AthleteDto> athls = athleteService
						.findAthletesByBib(bibProperty.getValue());
				tb.setVisible(athls.size() > 0);
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
