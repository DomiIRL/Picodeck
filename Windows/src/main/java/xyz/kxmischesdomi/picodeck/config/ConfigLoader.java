package xyz.kxmischesdomi.picodeck.config;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.*;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class ConfigLoader {

	public static final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create();

	private File file;
	private JsonObject jsonObject = new JsonObject();

	public ConfigLoader(File file) {
		this.file = file;
		loadJson();
	}

	public JsonObject getJson() {
		return jsonObject;
	}

	public synchronized void loadJson() {
		try {

			if (!file.exists()) {
				writeUpdatesToFile();
			}

			JsonReader jsonReader = new JsonReader(new FileReader(file));
			jsonObject = gson.fromJson(jsonReader, JsonObject.class);
		} catch (Exception exception) {

		}

	}

	public void overwriteFile(String newJsonData) {

		System.out.println(newJsonData);

		jsonObject = gson.fromJson(newJsonData, JsonObject.class);

		writeUpdatesToFile();
	}

	public void markDirty() {
		new Thread(this::writeUpdatesToFile).start();
	}

	private synchronized void writeUpdatesToFile() {

		try {

			if (!file.exists()) {
				file.createNewFile();
			}

			String output = gson.toJson(jsonObject);

			FileOutputStream outputStream = new FileOutputStream(file);
			byte[] bytes = output.getBytes();
			outputStream.write(bytes);
			outputStream.close();

		} catch (IOException exception) {
			exception.printStackTrace();
		}

	}

}
