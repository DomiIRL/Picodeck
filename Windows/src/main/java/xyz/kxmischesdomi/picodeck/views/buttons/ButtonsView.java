package xyz.kxmischesdomi.picodeck.views.buttons;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import xyz.kxmischesdomi.picodeck.config.Buttons;
import xyz.kxmischesdomi.picodeck.config.Config;
import xyz.kxmischesdomi.picodeck.config.Uploads;
import xyz.kxmischesdomi.picodeck.views.MainLayout;

import java.util.Map;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@PageTitle("Buttons")
@Route(value = "buttons", layout = MainLayout.class)
public class ButtonsView extends VerticalLayout {

	private FlexLayout buttonsLayout;

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		buttonsLayout = new FlexLayout();
		buttonsLayout.setFlexWrap(FlexLayout.FlexWrap.WRAP);
		buttonsLayout.addClassNames(LumoUtility.Gap.LARGE);

		HorizontalLayout controls = new HorizontalLayout();

		Button button = new Button("New button");
		controls.add(button);

		add(controls, buttonsLayout);

		for (Map.Entry<String, JsonElement> entry : Buttons.getButtons().entrySet()) {
			String name = entry.getKey();
			JsonObject data = entry.getValue().getAsJsonObject();
			String icon = data.get("icon").getAsString();
			String src = Uploads.getUploadAsSrc(icon);
			renderButtonCell(name, src);
		}

	}

	private void renderButtonCell(String name, String src) {

		VerticalLayout layout = new VerticalLayout();
		layout.setSpacing(false);
		layout.setHeight("200px");
		layout.setWidth("200px");

		Button image = new Button();
		image.setWidthFull();
		image.setHeightFull();
		String source = String.format("url('%s')", src);
		System.out.println(source);
		image.getStyle()
				.set("background-image", source)
				.set("background-size", "cover")
				.set("background-position", "center");

		image.addClickListener(event -> {
			// TODO: Open edit page
		});

		Paragraph buttonTitle = new Paragraph(name);
		buttonTitle.addClassNames(LumoUtility.TextAlignment.CENTER, LumoUtility.Margin.NONE, LumoUtility.FontSize.XLARGE);
		buttonTitle.setWidth("100%");

		layout.add(image, buttonTitle);
		buttonsLayout.add(layout);

	}

}
