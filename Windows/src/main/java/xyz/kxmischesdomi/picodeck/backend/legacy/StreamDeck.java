package xyz.kxmischesdomi.picodeck.backend.legacy;

import xyz.kxmischesdomi.picodeck.backend.legacy.serial.SerialConnector;
import xyz.kxmischesdomi.picodeck.backend.legacy.service.IBackendService;
import xyz.kxmischesdomi.picodeck.backend.legacy.service.ICommunicationService;
import com.fazecast.jSerialComm.SerialPort;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class StreamDeck implements IBackendService, ICommunicationService {

	private final SerialConnector serialConnector;

	public StreamDeck(SerialPort serialPort) {
		serialConnector = new SerialConnector(serialPort);
	}

	@Override
	public void sendMessage(String message) {
		serialConnector.sendMessage(message);
	}

	public void init() {
		serialConnector.connect();
	}

	public void onTerminate() {
		serialConnector.disconnect();
	}

}
