package xyz.kxmischesdomi.picodeck.config;

import com.google.gson.JsonObject;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class Buttons {

	private static final JsonObject buttons;

	public static JsonObject getButtons() {
		return buttons;
	}

	static {
		JsonObject json = Config.getDeviceConfig().getJson();
		JsonObject jsonObject = json.getAsJsonObject("buttons");
		if (jsonObject == null) {
			buttons = new JsonObject();
			json.add("buttons", buttons);
		} else {
			buttons = jsonObject;
		}
	}

}
