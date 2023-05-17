package xyz.kxmischesdomi.picodeck.views.settings;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import xyz.kxmischesdomi.picodeck.views.MainLayout;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@PageTitle("Settings")
@Route(value = "settings", layout = MainLayout.class)
public class SettingsView extends VerticalLayout {

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		addCategory("Device", new DeviceComponent());
		addCategory("Configuration", new ConfigurationComponent());
	}

	private void addCategory(String title, Component category) {

		VerticalLayout layout = new VerticalLayout();
		H2 categoryTitle = new H2(title);

		category.addClassNames(LumoUtility.Padding.NONE);

		layout.add(categoryTitle, category);
		add(layout);
	}

}
