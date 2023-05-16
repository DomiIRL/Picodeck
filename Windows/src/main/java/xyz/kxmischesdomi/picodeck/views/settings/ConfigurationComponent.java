package xyz.kxmischesdomi.picodeck.views.settings;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

import java.io.InputStream;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class ConfigurationComponent extends VerticalLayout {

	public ConfigurationComponent() {

		Button exportButton = new Button("Export Configuration");

		// ---------------------------------------

		Paragraph hint = new Paragraph("Accepted file formats: JSON (.json)");
		hint.getStyle().set("color", "var(--lumo-secondary-text-color)");

		MemoryBuffer buffer = new MemoryBuffer();
		Upload upload = new Upload(buffer);
		upload.setAcceptedFileTypes(".json");

//		upload.setDropLabel(new Label("Drop .json config here"));

		upload.addSucceededListener(event -> {
			String fileName = event.getFileName();
			InputStream inputStream = buffer.getInputStream();

			System.out.println(fileName);
		});

		VerticalLayout verticalLayout = new VerticalLayout(hint, upload);
		verticalLayout.setPadding(false);
		verticalLayout.setSpacing(false);

		// --------------------------------------

		add(exportButton, verticalLayout);
	}

}
