package xyz.kxmischesdomi.picodeck;

import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import xyz.kxmischesdomi.picodeck.config.Config;

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
	public void configurePage(AppShellSettings settings) {
		settings.addLink("shortcut icon", "icons/favicon.png");
		settings.addFavIcon("icon", "icons/favicon.png", "192x192");
	}

	@Override
	public void serviceInit(ServiceInitEvent serviceInitEvent) {
		SpringApplication.getShutdownHandlers().add(() -> {
			Config.getDeviceConfig().markDirty();
			Config.getAppConfig().markDirty();
		});
	}

}
