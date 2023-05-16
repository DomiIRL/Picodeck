package xyz.kxmischesdomi.picodeck.backend.legacy.serial;

import xyz.kxmischesdomi.picodeck.backend.legacy.service.ICommunicationService;
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class SerialConnector implements ICommunicationService {

	public final SerialPort port;

	private boolean stopThread = false;
	private Thread communicationThread;

	public SerialConnector(SerialPort port) {
		this.port = port;
	}

	public void connect() {

		port.openPort();
		System.out.println("open port to " + port.getDescriptivePortName());
		port.setBaudRate(2000000);

		communicationThread = new Thread(this::listen);
		communicationThread.start();

	}

	public void disconnect() {
		System.out.println("kill connection");
		stopThread = true;
		port.closePort();
	}

	@Override
	public void sendMessage(String message) {
		byte[] bytes = (message + "\n").getBytes();
		port.writeBytes(bytes, bytes.length);
		System.out.println("send " + message);
	}

	private void listen() {
		while (true) {
			if (stopThread) break;

			while (port.bytesAvailable() > 0) {
				byte[] readBuffer = new byte[port.bytesAvailable()];
				int read = port.readBytes(readBuffer, readBuffer.length);
				String s = new String(readBuffer);
				System.out.println("Received " + s);

				if (s.equals("open calculator")) {
					try {
						Runtime.getRuntime().exec("calc");
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				}
			}

		}

	}

}
