package xyz.kxmischesdomi.picodeck.backend.legacy;

import xyz.kxmischesdomi.picodeck.backend.legacy.service.IBackendService;
import xyz.kxmischesdomi.picodeck.backend.legacy.service.ICommunicationService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class DeviceHolder implements IBackendService, ICommunicationService {

	private final List<StreamDeck> devices;

	public DeviceHolder() {
		devices = new ArrayList<>();
	}

	@Override
	public void sendMessage(String message) {
		for (StreamDeck device : devices) {
			device.sendMessage(message);
		}
	}

	@Override
	public void onTerminate() {
		for (StreamDeck device : devices) {
			device.onTerminate();
		}
	}

	public List<StreamDeck> getDevices() {
		return devices;
	}

}
