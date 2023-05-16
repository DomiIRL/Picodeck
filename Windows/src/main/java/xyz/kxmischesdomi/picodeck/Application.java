package xyz.kxmischesdomi.picodeck;

import com.vaadin.flow.theme.lumo.Lumo;
import xyz.kxmischesdomi.picodeck.backend.legacy.BackendService;
import xyz.kxmischesdomi.picodeck.backend.legacy.StreamDeck;
import com.fazecast.jSerialComm.SerialPort;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 *
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "picodeck", variant = Lumo.DARK)
public class Application implements AppShellConfigurator, VaadinServiceInitListener {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void serviceInit(ServiceInitEvent serviceInitEvent) {

//		try {
//			BackendService.setInstance(new BackendService());
//
//			System.out.println("--------------------------------------------");
//			System.out.println("List COM ports");
//			System.out.println("--------------------------------------------");
//			SerialPort[] comPorts = SerialPort.getCommPorts();
//			for (int i = 0; i < comPorts.length; i++) {
//				System.out.println("comPorts[" + i + "] = " + comPorts[i].getDescriptivePortName());
//			}
//			System.out.println("--------------------------------------------");
//
//			StreamDeck deck = new StreamDeck(comPorts[3]);
//			deck.init();
//			BackendService.getInstance().getDeviceHolder().getDevices().add(deck);
//
//			SpringApplication.getShutdownHandlers().add(() -> {
//				BackendService.getInstance().onTerminate();
//			});
//		} catch (Exception exception) {
//			exception.printStackTrace();
//		}

	}

}
