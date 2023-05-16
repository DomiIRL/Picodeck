package xyz.kxmischesdomi.picodeck.views.settings;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.server.StreamResource;
import org.vaadin.olli.FileDownloadWrapper;

import java.io.*;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class ConfigurationComponent extends VerticalLayout {

	public ConfigurationComponent() {
		setSpacing(false);

		Button exportButton = new Button("Export Configuration");

		FileDownloadWrapper link = new FileDownloadWrapper(new StreamResource("device.json", () -> {
			try {
				return new FileInputStream("config/device.json");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return InputStream.nullInputStream();
		}));
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
				FileOutputStream outputStream = new FileOutputStream("config/device.json");

				byte[] byteBuffer = new byte[inputStream.available()]; // Set buffer size based on input stream size

				int bytesRead = inputStream.read(byteBuffer);
				outputStream.write(byteBuffer, 0, bytesRead);

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
