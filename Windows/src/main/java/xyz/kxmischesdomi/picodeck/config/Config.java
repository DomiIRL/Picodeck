package xyz.kxmischesdomi.picodeck.config;

import java.io.File;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class Config {

	private static final ConfigLoader deviceConfig = new ConfigLoader(new File("config/device.json"));
	private static final ConfigLoader appConfig = new ConfigLoader(new File("config/config.json"));

	public static ConfigLoader getDeviceConfig() {
		return deviceConfig;
	}

	public static ConfigLoader getAppConfig() {
		return appConfig;
	}

	static {
		deviceConfig.loadJson();
		appConfig.loadJson();
	}

}
