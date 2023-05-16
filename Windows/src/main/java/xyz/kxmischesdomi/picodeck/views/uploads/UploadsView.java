package xyz.kxmischesdomi.picodeck.views.uploads;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.BoxSizing;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.apache.commons.io.IOUtils;
import xyz.kxmischesdomi.picodeck.views.MainLayout;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@PageTitle("Uploads")
@Route(value = "uploads", layout = MainLayout.class)
public class UploadsView extends VerticalLayout {

	public static Map<String, String> images = new TreeMap<>();

	public UploadsView() {

		Paragraph hint = new Paragraph("Accepted file formats: PNG, JPEG, GIF, WEBP (.png, .jpg, .jpeg, .gif, .webp)");
		hint.getStyle().set("color", "var(--lumo-secondary-text-color)");

		MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
		Upload upload = new Upload(buffer);
		upload.setAcceptedFileTypes("image/png", "image/jpeg", "image/gif", "image/webp");



		FlexLayout imageGrid = new FlexLayout();
		imageGrid.setFlexWrap(FlexLayout.FlexWrap.WRAP);
		imageGrid.getStyle().set("gap", "var(--lumo-space-xl)");

		upload.addSucceededListener(event -> {
			String fileName = event.getFileName();
			InputStream inputStream = buffer.getInputStream(fileName);

			fileName = fileName.substring(0, fileName.lastIndexOf('.'));

			if (images.containsKey(fileName)) {
				Notification notification = Notification.show("Image name already exists. Please rename");
				notification.setPosition(Notification.Position.TOP_CENTER);
				notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
				return;
			}

			try {
				byte[] sourceBytes = compressImage(IOUtils.toByteArray(inputStream));

				String encodedString = Base64.getEncoder().encodeToString(sourceBytes);
				assertNotNull(encodedString);

				if (fileName.length() > 20) {
					fileName = fileName.substring(0, 20);
				}


				images.put(fileName, encodedString);

				imageGrid.add(createImageCell(fileName, encodedString));

			} catch (IOException exception) {
				exception.printStackTrace();
			}

		});

		VerticalLayout uploadLayout = new VerticalLayout(hint, upload);
		uploadLayout.setPadding(false);
		uploadLayout.setSpacing(false);


		add(uploadLayout, imageGrid);

		images.forEach((s, s2) -> {
			imageGrid.add(createImageCell(s, s2));
		});
	}

	private Component createImageCell(String name, String base64) {
		String source = String.format("data:image/png;base64,%s", base64);

		Div imageWrapper = new Div();
		imageWrapper.getStyle()
				.set("background-image", String.format("url('%s')", source))
				.set("background-size", "contain")
				.set("background-repeat", "no-repeat")
				.set("background-position", "center");
		imageWrapper.setWidth("200px");
		imageWrapper.setHeight("200px");

		Label imageTitle = new Label(name);
		imageTitle.addClassNames(LumoUtility.TextAlignment.CENTER);
		imageTitle.setWidth("100%");

		VerticalLayout cell = new VerticalLayout(imageWrapper, imageTitle);
		cell.setWidth("auto");
		cell.setHeight("auto");

		cell.setBoxSizing(BoxSizing.BORDER_BOX);
		cell.addClassNames(LumoUtility.Width.LARGE ,LumoUtility.Background.CONTRAST_10, LumoUtility.BorderRadius.LARGE, LumoUtility.AlignItems.CENTER, LumoUtility.Padding.MEDIUM);

		return cell;
	}

	private static byte[] compressImage(byte[] imageData) throws IOException {
		try {
			// Read the image from the byte array
			ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
			BufferedImage image = ImageIO.read(inputStream);

			// Create a ByteArrayOutputStream to hold the compressed image data
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			// Write the image to the ByteArrayOutputStream using lossless PNG compression
			ImageIO.write(image, "png", outputStream);

			// Close the streams
			inputStream.close();
			outputStream.close();

			// Return the compressed image data as a byte array
			return outputStream.toByteArray();
		} catch (Exception exception) {
			return imageData;
		}

	}


}
