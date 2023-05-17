package xyz.kxmischesdomi.picodeck.config;

import com.google.gson.JsonObject;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class Mappings {

	private static final JsonObject buttons;

	public static JsonObject getButtons() {
		return buttons;
	}

	static {
		JsonObject json = Config.getDeviceConfig().getJson();
		JsonObject jsonObject = json.getAsJsonObject("mappings");
		if (jsonObject == null) {
			buttons = new JsonObject();
			json.add("mappings", buttons);
		} else {
			buttons = jsonObject;
		}
	}

}
