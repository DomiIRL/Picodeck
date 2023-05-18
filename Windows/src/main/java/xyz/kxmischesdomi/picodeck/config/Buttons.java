package xyz.kxmischesdomi.picodeck.config;

import com.google.gson.JsonObject;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class Buttons {

	public static record ConfiguredButton(String name, Uploads.Upload icon) { }

	private static final JsonObject buttons;

	public static JsonObject getButtons() {
		return buttons;
	}

	public static ConfiguredButton getButton(String name) {
		if (getButtons().has(name)) {
			JsonObject object = getButtons().getAsJsonObject(name);
			return new ConfiguredButton(name, Uploads.getUpload(object.get("icon").getAsString()));
		}

		return null;
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
