package xyz.kxmischesdomi.picodeck.views.settings;

import com.fazecast.jSerialComm.SerialPort;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import xyz.kxmischesdomi.picodeck.serial.SerialConnection;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class DeviceComponent extends VerticalLayout {

	public DeviceComponent() {
		setSpacing(false);

		Select<String> deviceSelect = new Select<>();
		deviceSelect.getStyle().set("width", "275px");
		deviceSelect.setLabel("Select port");
		setItems(deviceSelect);

		deviceSelect.setItemLabelGenerator(s -> getPortByName(s).getDescriptivePortName());

		if (SerialConnection.getCurrentConnection() != null) {
			deviceSelect.setValue(SerialConnection.getCurrentConnection().getSystemPortName());
		}

		deviceSelect.addValueChangeListener(valueChangeEvent -> {
			SerialConnection.setCurrentConnection(getPortByName(valueChangeEvent.getValue()));
		});

		Button testButton = new Button("Test Connection", buttonClickEvent -> {

			buttonClickEvent.getSource().setEnabled(false);

			String name = "Handshake failed";
			NotificationVariant variant = NotificationVariant.LUMO_ERROR;

			if (new Random().nextBoolean()) {
				name = "Handshake sucessful";

				variant = NotificationVariant.LUMO_SUCCESS;
			}

			Notification notification = Notification.show(name);
			notification.addThemeVariants(variant);
			notification.setPosition(Notification.Position.TOP_CENTER);
			notification.setDuration(1000);

			notification.addDetachListener(detachEvent -> {
				buttonClickEvent.getSource().setEnabled(true);
			});

		});

		add(deviceSelect, testButton);
	}

	private SerialPort getPortByName(String name) {
		return SerialPort.getCommPort(name);
	}

	private void setItems(Select<String> select) {
		select.setItems(Arrays.stream(SerialPort.getCommPorts()).map(SerialPort::getSystemPortName).collect(Collectors.toList()));
	}

}
