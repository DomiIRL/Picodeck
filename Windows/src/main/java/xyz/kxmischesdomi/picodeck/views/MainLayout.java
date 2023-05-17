package xyz.kxmischesdomi.picodeck.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Svg;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.Lumo;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.vaadin.lineawesome.LineAwesomeIcon;
import xyz.kxmischesdomi.picodeck.components.appnav.AppNav;
import xyz.kxmischesdomi.picodeck.components.appnav.AppNavItem;
import xyz.kxmischesdomi.picodeck.views.buttons.ButtonsView;
import xyz.kxmischesdomi.picodeck.views.mappings.MappingsView;
import xyz.kxmischesdomi.picodeck.views.modules.ModulesView;
import xyz.kxmischesdomi.picodeck.views.settings.SettingsView;
import xyz.kxmischesdomi.picodeck.views.splash.SplashView;
import xyz.kxmischesdomi.picodeck.views.uploads.UploadsView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author KxmischesDomi | https://github.com/kxmischesdomi
 * @since 1.0
 */
@PageTitle("PicoDeck")
@Route(value = "")
public class MainLayout extends AppLayout {

    private H2 viewTitle;

    public MainLayout() {
        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {

    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
//        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
        viewTitle.getStyle().set("margin-left", "var(--lumo-space-s)");

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {


        H1 appName = new H1("Picodeck");
        appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE, LumoUtility.TextAlignment.CENTER, LumoUtility.Padding.MEDIUM);

        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
    }

    private AppNav createNavigation() {
        AppNav nav = new AppNav();

        nav.addItem(new AppNavItem("Mappings", MappingsView.class, createIcon(VaadinIcon.SITEMAP.create())));
        nav.addItem(new AppNavItem("Buttons", ButtonsView.class, createIcon(VaadinIcon.GRID_BIG_O.create())));
        nav.addItem(new AppNavItem("Uploads", UploadsView.class, createIcon(VaadinIcon.UPLOAD.create())));
        nav.addItem(new AppNavItem("Modules", ModulesView.class, createIcon(VaadinIcon.CUBES.create())));
        nav.addItem(new AppNavItem("Splash", SplashView.class, createIcon(VaadinIcon.PAINT_ROLL.create())));
        nav.addItem(new AppNavItem("Settings", SettingsView.class, createIcon(VaadinIcon.COGS.create())));

        return nav;
    }

    private Component createIcon(Component icon) {
        icon.getStyle().set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("padding", "var(--lumo-space-xs)");
        return icon;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }

}
