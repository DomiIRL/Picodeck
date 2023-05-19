package xyz.kxmischesdomi.picodeck.views.buttons;

import com.google.gson.JsonObject;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;
import xyz.kxmischesdomi.picodeck.components.SuccessButton;
import xyz.kxmischesdomi.picodeck.components.NeutralButton;
import xyz.kxmischesdomi.picodeck.components.ErrorButton;
import xyz.kxmischesdomi.picodeck.config.Buttons;
import xyz.kxmischesdomi.picodeck.config.Config;
import xyz.kxmischesdomi.picodeck.config.Uploads;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class EditButtonDialog extends Dialog {

	private final Runnable onDataUpdate;

	public EditButtonDialog(Runnable onDataUpdate) {
		this.onDataUpdate = onDataUpdate;
	}

	public void init(Buttons.ConfiguredButton configuredButton) {
		removeAll();

		setHeaderTitle("Edit button");

		FormLayout form = new FormLayout();

		TextField nameField = new TextField("Name");
		nameField.setClearButtonVisible(true);
		nameField.setRequiredIndicatorVisible(true);
		nameField.setRequired(true);
		ComboBox<String> iconInput = new ComboBox<>("Icon");
		iconInput.setRequiredIndicatorVisible(true);
		iconInput.setItems(Uploads.getUploads().keySet());
		iconInput.setAllowCustomValue(true);
		iconInput.setClearButtonVisible(true);
		iconInput.setRequired(true);

		if (configuredButton != null) {
			nameField.setValue(configuredButton.name());
			iconInput.setValue(configuredButton.icon().name());
		}

		Button saveButton = new SuccessButton("Save");
		saveButton.addClickListener(event -> {

			if (nameField.isInvalid() || iconInput.isInvalid()) {
				return;
			}

			String name = nameField.getValue();

			if (configuredButton == null || !name.equals(configuredButton.name())) {
				if (Buttons.getButtons().has(name)) {
					Notification notification = Notification.show(String.format("Name '%s' is already defined as a button", name));
					notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
					notification.setPosition(Notification.Position.TOP_CENTER);
					return;
				}
			}

			JsonObject buttonObject = new JsonObject();
			buttonObject.addProperty("icon", iconInput.getValue());
			Buttons.getButtons().add(name, buttonObject);
			Config.getDeviceConfig().markDirty();

			close();
			onDataUpdate.run();
		});
		Button deleteButton = new ErrorButton("Delete");
		deleteButton.addClickListener(event -> {
			if (configuredButton != null) {
				Buttons.getButtons().remove(configuredButton.name());
				Config.getDeviceConfig().markDirty();
			}
			close();
			onDataUpdate.run();
		});
		Button discardButton = new NeutralButton("Discard");
		discardButton.addClickListener(event -> {
			close();
		});

		HorizontalLayout buttons = new HorizontalLayout(saveButton, discardButton, deleteButton);
		buttons.addClassNames(LumoUtility.Margin.Top.MEDIUM);
		buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
		buttons.setFlexGrow(1, saveButton, discardButton, deleteButton);

		form.add(nameField, iconInput, buttons);
		form.setColspan(nameField, 1);
		form.setColspan(iconInput, 1);
		form.setColspan(buttons, 2);
		add(form);
	}

}
