package xyz.kxmischesdomi.picodeck.components;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
public class SuccessButton extends Button {

	public SuccessButton() {
		super();
		style();
	}

	public SuccessButton(String text) {
		super(text);
		style();
	}

	public SuccessButton(Component icon) {
		super(icon);
		style();
	}

	public SuccessButton(String text, Component icon) {
		super(text, icon);
		style();
	}

	public SuccessButton(String text, ComponentEventListener<ClickEvent<Button>> clickListener) {
		super(text, clickListener);
		style();
	}

	public SuccessButton(Component icon, ComponentEventListener<ClickEvent<Button>> clickListener) {
		super(icon, clickListener);
		style();
	}

	public SuccessButton(String text, Component icon, ComponentEventListener<ClickEvent<Button>> clickListener) {
		super(text, icon, clickListener);
		style();
	}

	private void style() {
		addClassNames(LumoUtility.Background.SUCCESS, LumoUtility.TextColor.PRIMARY_CONTRAST);
	}

}
