package xyz.kxmischesdomi.picodeck.views.mappings;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropEffect;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import xyz.kxmischesdomi.picodeck.config.Buttons;
import xyz.kxmischesdomi.picodeck.views.MainLayout;

import java.util.Random;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@PageTitle("Mappings")
@Route(value = "mappings", layout = MainLayout.class)
public class MappingsView extends VerticalLayout {

	private static final int columns = 4, rows = 2;

	private final Dialog dialog = new Dialog();
	private final ComboBox<String> mappingSelect = new ComboBox<>("Button");
	private final Button saveButton = new Button("Save");
	private final Button clearButton = new Button("Clear");

	@Override
	protected void onAttach(AttachEvent attachEvent) {

		dialog.setHeaderTitle("Edit Mapping");
		mappingSelect.setWidth("300px");
		mappingSelect.setLabel("Select button");
		Button discardButton = new Button("Discard");
		discardButton.addClickListener(event -> dialog.close());
		HorizontalLayout horizontalLayout = new HorizontalLayout(saveButton, clearButton, discardButton);
		horizontalLayout.setJustifyContentMode(JustifyContentMode.CENTER);
		horizontalLayout.setFlexGrow(1, saveButton, discardButton, clearButton);
		dialog.add(mappingSelect, horizontalLayout);

		add(dialog);

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
				int index = ((y + (pageIndex * rows)) * columns + x);

				Button button = new Button(String.valueOf(index));

				DragSource<Button> buttonDragSource = DragSource.create(button);
				buttonDragSource.setDragData(index);

				DropTarget<Button> buttonDropTarget = DropTarget.create(button);
				buttonDropTarget.setDropEffect(DropEffect.MOVE);

				buttonDropTarget.addDropListener(buttonDropEvent -> {

					buttonDropEvent.getDragData().ifPresent(data -> {

						int from = (int) data;
						int to = index;

						buttonDropEvent.getDragSourceComponent().ifPresent(component -> {
							Button fromComponent = (Button) component;
						});

					});

				});

				button.addClickListener(buttonClickEvent -> {
					fillDialog(index);
					dialog.open();

				});

				String size = "5rem";
				button.addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.MinWidth.NONE, LumoUtility.MinHeight.NONE, LumoUtility.Margin.NONE);
				button.getStyle().set("width", size).set("height", size);
				horizontalLayout.add(button);
			}

		}
		return verticalLayout;
	}

	private void fillDialog(int mappingIndex) {
		String currentButton = "youtube";

		mappingSelect.setItems(Buttons.getButtons().keySet());
//		mappingSelect.setItemLabelGenerator(s -> );
		mappingSelect.setValue(currentButton);

		saveButton.addClickListener(event -> {
			// TODO: SAVE CHANGES
		});

		clearButton.addClickListener(event -> {
			// TODO: DELETE MAPPING
		});

	}

}
