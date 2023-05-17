package xyz.kxmischesdomi.picodeck.views.buttons;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import xyz.kxmischesdomi.picodeck.views.MainLayout;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@PageTitle("Mappings")
@Route(value = "button/edit", layout = MainLayout.class)
public class EditButtonView extends VerticalLayout implements HasUrlParameter<String> {

	private String parameter;

	public EditButtonView() {

	}

	@Override
	public void setParameter(BeforeEvent beforeEvent, String s) {
		this.parameter = parameter;
	}

}
