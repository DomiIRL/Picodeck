package xyz.kxmischesdomi.picodeck.views.buttons;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import xyz.kxmischesdomi.picodeck.views.MainLayout;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@PageTitle("Buttons")
@Route(value = "buttons", layout = MainLayout.class)
public class ButtonsView extends VerticalLayout {

	private static final int columns = 4, rows = 2;

	public ButtonsView() {

		FlexLayout grids = new FlexLayout();
		grids.setFlexWrap(FlexLayout.FlexWrap.WRAP);
		add(grids);

		for (int page = 0; page < 10; page++) {
			Component grid = createButtonsGridForPage(page);
			grids.add(grid);
		}

	}

	private Component createButtonsGridForPage(int pageIndex) {
		H2 gridTitle = new H2(String.format("Page %s", pageIndex + 1));

		VerticalLayout verticalLayout = new VerticalLayout(gridTitle);
		verticalLayout.setWidth("auto");

		for (int y = 0; y < 2; y++) {
			HorizontalLayout horizontalLayout = new HorizontalLayout();
			verticalLayout.add(horizontalLayout);

			for (int x = 0; x < 4; x++) {
				Button button = new Button();

				int finalX = x;
				int finalY = y + (pageIndex * rows);
				button.addClickListener(buttonClickEvent -> {
					int index = (finalY * columns + finalX);
					Notification.show(String.valueOf(index));
				});

				String size = "5rem";
				button.addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.MinWidth.NONE, LumoUtility.MinHeight.NONE, LumoUtility.Margin.NONE);
				button.getStyle().set("width", size).set("height", size);
				horizontalLayout.add(button);
			}

		}
		return verticalLayout;
	}

}
