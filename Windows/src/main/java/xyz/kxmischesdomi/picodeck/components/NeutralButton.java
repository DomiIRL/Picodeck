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
public class NeutralButton extends Button {

	public NeutralButton() {
		super();
		style();
	}

	public NeutralButton(String text) {
		super(text);
		style();
	}

	public NeutralButton(Component icon) {
		super(icon);
		style();
	}

	public NeutralButton(String text, Component icon) {
		super(text, icon);
		style();
	}

	public NeutralButton(String text, ComponentEventListener<ClickEvent<Button>> clickListener) {
		super(text, clickListener);
		style();
	}

	public NeutralButton(Component icon, ComponentEventListener<ClickEvent<Button>> clickListener) {
		super(icon, clickListener);
		style();
	}

	public NeutralButton(String text, Component icon, ComponentEventListener<ClickEvent<Button>> clickListener) {
		super(text, icon, clickListener);
		style();
	}

	private void style() {
	}

}
