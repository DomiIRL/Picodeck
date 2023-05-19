package xyz.kxmischesdomi.picodeck.views.mappings;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.dnd.DragSource;
import com.vaadin.flow.component.dnd.DropEffect;
import com.vaadin.flow.component.dnd.DropTarget;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import xyz.kxmischesdomi.picodeck.components.ErrorButton;
import xyz.kxmischesdomi.picodeck.components.NeutralButton;
import xyz.kxmischesdomi.picodeck.components.SuccessButton;
import xyz.kxmischesdomi.picodeck.config.Buttons;
import xyz.kxmischesdomi.picodeck.config.Config;
import xyz.kxmischesdomi.picodeck.config.Mappings;
import xyz.kxmischesdomi.picodeck.config.Uploads;
import xyz.kxmischesdomi.picodeck.views.MainLayout;

import javax.annotation.Nullable;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@PageTitle("Mappings")
@Route(value = "mappings", layout = MainLayout.class)
public class MappingsView extends SplitLayout {

	private static final int columns = 4, rows = 2;

	FlexLayout grids = new FlexLayout();

	private final Dialog dialog = new Dialog();

	int gridsToRender;

	public MappingsView() {
		setOrientation(Orientation.HORIZONTAL);
		setSplitterPosition(70);
		setWidth("100%");
		setHeight("100%");

		int highestIndex = -1;
		for (String s : Mappings.getMappings().keySet()) {

			try {
				int i = Integer.parseInt(s);
				if (i > highestIndex) {
					highestIndex = i;
				}
			} catch (Exception exception) { }

		}
		gridsToRender = (int) Math.max(3, Math.ceil((double) highestIndex /  (columns * rows)));


		VerticalLayout view = createMappingsView();

		Scroller scroller = new Scroller(view);
		scroller.setHeight("100%");
		addToPrimary(scroller);

		Scroller scroller1 = new Scroller(createButtonsView());
		scroller1.setHeight("100%");
		addToSecondary(scroller1);

	}

	private VerticalLayout createMappingsView() {

		Button button = new Button("New Page");
		button.addClickListener(event -> {
			gridsToRender++;
			renderGrids();
		});

		grids.setFlexWrap(FlexLayout.FlexWrap.WRAP);

		renderGrids();

		return new VerticalLayout(button, grids, dialog);
	}

	private Component createButtonsView() {

		Label title = new Label("Drag and Drop from here");

		FlexLayout layout = new FlexLayout();
		layout.setAlignContent(FlexLayout.ContentAlignment.START);
		layout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
		layout.addClassNames(LumoUtility.Gap.SMALL);
		layout.setFlexWrap(FlexLayout.FlexWrap.WRAP);

		for (String buttonName : Buttons.getButtons().keySet()) {
			Button button = createButton(-1, Buttons.getButton(buttonName));
			layout.add(button);
		}

		return new VerticalLayout(title, layout);
	}

	private void renderGrids() {
		grids.removeAll();
		for (int page = 0; page < gridsToRender; page++) {
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

				Button button = createButton(index, Mappings.getMapping(index));
				button.getElement().setAttribute("mapping", "");

				DropTarget<Button> buttonDropTarget = DropTarget.create(button);
				buttonDropTarget.setDropEffect(DropEffect.MOVE);

				buttonDropTarget.addDropListener(buttonDropEvent -> {

					buttonDropEvent.getDragData().ifPresent(data -> {

						DragButton dragButton = (DragButton) data;
						int to = index;

						if (dragButton.index != -1) {
							Mappings.getMappings().remove(String.valueOf(dragButton.index));
						}

						Mappings.getMappings().addProperty(String.valueOf(to), dragButton.configuredButton().name());
						Config.getDeviceConfig().markDirty();
						renderGrids();
					});

				});

				button.addClickListener(buttonClickEvent -> {
					setupDialog(index);
					dialog.open();
				});

				horizontalLayout.add(button);
			}

		}
		return verticalLayout;
	}

	private static record DragButton(int index, Buttons.ConfiguredButton configuredButton) {}

	private Button createButton(int index, @Nullable Buttons.ConfiguredButton configuredButton) {
		Button button = new Button();

		if (configuredButton != null) {
			Uploads.Upload icon = configuredButton.icon();

			if (icon != null) {
				button.setSizeFull();
				button.getStyle()
						.set("background-image", icon.base64AsSrc())
						.set("background-size", "cover")
						.set("background-position", "center");
			}
		}

		DragSource<Button> buttonDragSource = DragSource.create(button);
		buttonDragSource.setDragData(new DragButton(index, configuredButton));

		String size = "5rem";
		button.addClassNames(LumoUtility.BoxSizing.BORDER, LumoUtility.MinWidth.NONE, LumoUtility.MinHeight.NONE, LumoUtility.Margin.NONE, LumoUtility.BorderRadius.LARGE);
		button.getStyle().set("width", size).set("height", size);

		return button;
	}

	private void setupDialog(int mappingIndex) {
		dialog.removeAll();
		dialog.setHeaderTitle("Edit Mapping");

		Buttons.ConfiguredButton currentButton = Mappings.getMapping(mappingIndex);

		ComboBox<String> mappingSelect = new ComboBox<>("Button");
		mappingSelect.setRequired(true);
		mappingSelect.setWidth("300px");
		mappingSelect.setLabel("Select button");
		mappingSelect.setItems(Buttons.getButtons().keySet());
		if (currentButton != null) {
			mappingSelect.setValue(currentButton.name());
		}

		Button saveButton = new SuccessButton("Save");
		saveButton.addClickListener(event -> {
			String newValue = mappingSelect.getValue();

			Buttons.ConfiguredButton oldMapping = Mappings.getMapping(mappingIndex);
			if (oldMapping == null || !oldMapping.name().equals(newValue)) {
				Buttons.ConfiguredButton button = Buttons.getButton(newValue);
				if (button != null) {
					Mappings.getMappings().addProperty(String.valueOf(mappingIndex), newValue);
					Config.getDeviceConfig().markDirty();
				}
				renderGrids();
			}

			dialog.close();
		});
		Button clearButton = new ErrorButton("Clear");
		clearButton.addClickListener(event -> {
			Mappings.getMappings().remove(String.valueOf(mappingIndex));
			Config.getDeviceConfig().markDirty();
			renderGrids();
			dialog.close();
		});
		Button discardButton = new NeutralButton("Discard");
		discardButton.addClickListener(event -> dialog.close());

		HorizontalLayout horizontalLayout = new HorizontalLayout(saveButton, discardButton, clearButton);
		horizontalLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		horizontalLayout.setFlexGrow(1, saveButton, discardButton, clearButton);

		dialog.add(mappingSelect, horizontalLayout);

	}

}
