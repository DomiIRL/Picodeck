package xyz.kxmischesdomi.picodeck.config;

import com.google.gson.JsonObject;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class Mappings {

	private static final JsonObject buttons;

	public static JsonObject getMappings() {
		return buttons;
	}

	public static Buttons.ConfiguredButton getMapping(int index) {
		if (getMappings().has(String.valueOf(index))) {
			return Buttons.getButton(getMappings().get(String.valueOf(index)).getAsString());
		}
		return null;
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
