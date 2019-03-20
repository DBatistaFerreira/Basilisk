package com.basilisk.frontend.components;

import com.basilisk.Constants;
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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

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
        List<User> users = menuBarPresenter.getAllUsers();

        users.sort(new UserCompare());
        searchComboBox.setItems(users);
        searchComboBox.setAllowCustomValue(true);

        //Listener for when user is clicked in combo box
        searchComboBox.addValueChangeListener(event -> {
            System.out.println(searchComboBox.getElement().getText());
            User user = searchComboBox.getValue();
            if (!Objects.isNull(user)) {
                UI.getCurrent().navigate(Constants.PROFILE_ROUTE + user.getUsername());
            }
        });

        //Listener for when user presses enter on combo box
        searchComboBox.addCustomValueSetListener(event -> {
            List<User> searchedUsers = menuBarPresenter.searchByUsername(event.getDetail());
            searchedUsers.sort(new UserCompare());
            User user = searchedUsers.get(0);
            UI.getCurrent().navigate(Constants.PROFILE_ROUTE + user.getUsername());
        });
    }

    @EventHandler
    private void navigateHome() {
        VaadinSession.getCurrent().setAttribute(Constants.USER_PROFILE, null);
        UI.getCurrent().navigate(Constants.HOME_ROUTE);
    }

    @EventHandler
    private void navigateProfile() {
        UI.getCurrent().navigate(Constants.PROFILE_ROUTE);
    }

    @EventHandler
    private void logoutClicked() {
        VaadinSession.getCurrent().setAttribute(Constants.CURRENT_USER, null);
        UI.getCurrent().navigate("");
    }

    public interface MenuBarComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }

    private class UserCompare implements Comparator<User> {

        @Override
        public int compare(User user1, User user2) {
            return user1.getUsername().compareToIgnoreCase(user2.getUsername());
        }
    }
}
