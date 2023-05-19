package xyz.kxmischesdomi.picodeck.views.uploads;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
import xyz.kxmischesdomi.picodeck.config.Config;
import xyz.kxmischesdomi.picodeck.config.Uploads;
import xyz.kxmischesdomi.picodeck.views.MainLayout;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Base64;
import java.util.Map;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@PageTitle("Uploads")
@Route(value = "uploads", layout = MainLayout.class)
public class UploadsView extends VerticalLayout {

	private FlexLayout imageGrid;

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		Paragraph hint = new Paragraph("Accepted file formats: PNG, JPEG, GIF, WEBP (.png, .jpg, .jpeg, .gif, .webp)");
		hint.getStyle().set("color", "var(--lumo-secondary-text-color)");

		MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
		Upload upload = new Upload(buffer);
		upload.setAcceptedFileTypes("image/png", "image/jpeg", "image/gif", "image/webp");

		imageGrid = new FlexLayout();
		imageGrid.setFlexWrap(FlexLayout.FlexWrap.WRAP);
		imageGrid.getStyle().set("gap", "var(--lumo-space-xl)");

		upload.addSucceededListener(event -> {
			String fileName = event.getFileName();
			InputStream inputStream = buffer.getInputStream(fileName);

			fileName = fileName.substring(0, fileName.lastIndexOf('.'));

			if (Uploads.getUploads().has(fileName)) {
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

				JsonObject newUploadMember = new JsonObject();
				newUploadMember.addProperty("base64", encodedString);
				Uploads.getUploads().add(fileName, newUploadMember);
				Config.getDeviceConfig().markDirty();


				// Make sure everything is alright
				reRenderImageGrid();

			} catch (IOException exception) {
				exception.printStackTrace();
			}

		});

		VerticalLayout uploadLayout = new VerticalLayout(hint, upload);
		uploadLayout.setPadding(false);
		uploadLayout.setSpacing(false);


		add(uploadLayout, imageGrid);

		reRenderImageGrid();

	}

	private void reRenderImageGrid() {
		Config.getDeviceConfig().markDirty();
		imageGrid.removeAll();
		for (Map.Entry<String, JsonElement> entry : Uploads.getUploads().entrySet()) {
			imageGrid.add(createImageCell(entry.getKey(), entry.getValue().getAsJsonObject().get("base64").getAsString()));
		}
	}

	private Component createImageCell(String name, String base64) {
		String source = String.format("data:image/png;base64,%s", base64);

		Div imageWrapper = new Div();
		imageWrapper.getStyle()
				.set("background-image", String.format("url('%s')", source))
				.set("background-size", "contain")
				.set("background-repeat", "no-repeat")
				.set("background-position", "center");
		imageWrapper.setWidth("190px");
		imageWrapper.setHeight("190px");
		imageWrapper.addClassNames(LumoUtility.BorderRadius.MEDIUM, LumoUtility.Margin.SMALL);


		Label fileSize = new Label(humanReadableByteCountBin(base64.getBytes().length));
		fileSize.addClassNames(LumoUtility.TextAlignment.CENTER, LumoUtility.Margin.NONE, LumoUtility.FontSize.SMALL);
		fileSize.setWidth("100%");

		Paragraph imageTitle = new Paragraph(name);
		imageTitle.addClassNames(LumoUtility.TextAlignment.CENTER, LumoUtility.Margin.NONE, LumoUtility.FontSize.XLARGE);
		imageTitle.setWidth("100%");
		VerticalLayout textLayout = new VerticalLayout(fileSize, imageTitle);
		textLayout.setSpacing(false);
		textLayout.setPadding(false);

		Button delete = new Button();
		Icon icon = VaadinIcon.CLOSE.create();
		icon.setColor("white");
		delete.setIcon(icon);
		delete.addClassNames(LumoUtility.Position.ABSOLUTE, LumoUtility.Background.ERROR);
		delete.setWidth("35px");
		delete.setHeight("35px");
		delete.getStyle().set("right", "-5px").set("top", "-5px").set("border-radius", "100%");

		delete.addClickListener(buttonClickEvent -> {
			Uploads.getUploads().remove(name);
			reRenderImageGrid();
		});

		VerticalLayout cell = new VerticalLayout(imageWrapper, textLayout, delete);
		cell.setWidth("250px");
		cell.setHeight("300px");
		cell.addClassNames(LumoUtility.Position.RELATIVE);

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

	public static String humanReadableByteCountBin(long bytes) {
		long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
		if (absB < 1024) {
			return bytes + " B";
		}
		long value = absB;
		CharacterIterator ci = new StringCharacterIterator("KMGTPE");
		for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
			value >>= 10;
			ci.next();
		}
		value *= Long.signum(bytes);
		return String.format("%.1f %ciB", value / 1024.0, ci.current());
	}

}
