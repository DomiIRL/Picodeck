package xyz.kxmischesdomi.picodeck.views.settings;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.StreamResource;
import org.vaadin.olli.FileDownloadWrapper;
import xyz.kxmischesdomi.picodeck.config.Config;
import xyz.kxmischesdomi.picodeck.config.ConfigLoader;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class ConfigurationComponent extends VerticalLayout {

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		setSpacing(false);

		Button exportButton = new Button("Export Configuration");

		FileDownloadWrapper link = new FileDownloadWrapper(new StreamResource("device.json", () -> new ByteArrayInputStream(ConfigLoader.gson.toJson(Config.getDeviceConfig().getJson()).getBytes())));
		link.wrapComponent(exportButton);

		// ---------------------------------------

		Paragraph hint = new Paragraph("Accepted file formats: JSON (.json)");
		hint.getStyle().set("color", "var(--lumo-secondary-text-color)");

		MemoryBuffer buffer = new MemoryBuffer();
		Upload upload = new Upload(buffer);
		upload.setAcceptedFileTypes(".json");

		upload.addSucceededListener(event -> {
			String fileName = event.getFileName();
			InputStream inputStream = buffer.getInputStream();

			try {
				Config.getDeviceConfig().overwriteFile(new String(inputStream.readAllBytes(), StandardCharsets.UTF_8));
			} catch (IOException e) {
				e.printStackTrace();
			}

		});

		VerticalLayout verticalLayout = new VerticalLayout(hint, upload);
		verticalLayout.setPadding(false);
		verticalLayout.setSpacing(false);

		// --------------------------------------

		add(link, verticalLayout);
	}
}
