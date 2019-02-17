package com.basilisk.frontend.components;

import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.MenuBarPresenter;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("menu-bar-component")
@HtmlImport("menu-bar-component.html")
public class MenuBarComponent extends PolymerTemplate<MenuBarComponent.MenuBarComponentModel> {

    @Id("homeTab")
    private Tab homeTab;
    @Id("profileTab")
    private Tab profileTab;
    @Id("searchComboBox")
    private ComboBox<User> searchComboBox;
    @Id("logoutTab")
    private Tab logoutTab;

    public MenuBarComponent(MenuBarPresenter menuBarPresenter) {
        // You can initialise any data required for the connected UI components here.
        searchComboBox.setItemLabelGenerator(User::getUsername);
        searchComboBox.setAllowCustomValue(true);
        searchComboBox.setItems(menuBarPresenter.getAllUsers());
        searchComboBox.addValueChangeListener(event -> {
            User user = (User) searchComboBox.getValue();
            UI.getCurrent().navigate("profile/" + user.getUsername());
        });
    }

    @EventHandler
    private void navigateHome() {
        VaadinSession.getCurrent().setAttribute("userProfile", null);
        UI.getCurrent().navigate("home");
    }

    @EventHandler
    private void navigateProfile() {
        UI.getCurrent().navigate("profile");
    }

    @EventHandler
    private void logoutClicked() {
        VaadinSession.getCurrent().setAttribute("currentUser", null);
        UI.getCurrent().navigate("");
    }

    public interface MenuBarComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
