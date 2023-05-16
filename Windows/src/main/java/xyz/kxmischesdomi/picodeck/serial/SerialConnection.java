package xyz.kxmischesdomi.picodeck.serial;

import com.fazecast.jSerialComm.SerialPort;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class SerialConnection {

	public static final int BAUDRATE = 2000000;

	private static SerialPort currentConnection;

	private static boolean stopReadThread = false;

	public static void setCurrentConnection(SerialPort currentConnection) {
		if (SerialConnection.currentConnection != null) {
			SerialConnection.currentConnection.closePort();
		}
		if (currentConnection != null) {
			currentConnection.openPort();
			currentConnection.setBaudRate(BAUDRATE);
		}
		SerialConnection.currentConnection = currentConnection;
	}

	public static SerialPort getCurrentConnection() {
		return currentConnection;
	}

	public static void stopReadThread(boolean stopReadThread) {
		SerialConnection.stopReadThread = stopReadThread;
	}

	public static void sendMessage(String message) {
		byte[] bytes = (message + "\n").getBytes();
		currentConnection.writeBytes(bytes, bytes.length);
		System.out.println("send " + message);
	}

	static {
		new Thread(() -> {
			while (true) {
				if (stopReadThread) return;
				if (currentConnection == null) continue;

				while (currentConnection.bytesAvailable() > 0) {
					byte[] readBuffer = new byte[currentConnection.bytesAvailable()];
					int read = currentConnection.readBytes(readBuffer, readBuffer.length);

					String s = new String(readBuffer);
					System.out.println("Received " + s);
				}

			}
		}).start();
	}

}
