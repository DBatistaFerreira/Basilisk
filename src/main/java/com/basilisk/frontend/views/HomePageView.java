package com.basilisk.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("Home")

public class HomePageView extends VerticalLayout {
    Integer textAreaCounter = 10;
    String hexTurquoise = "#40E0D0";

    private Tab home;
    private Tab profile;
    private Tab logout;
    private Tabs tabs;

    private HorizontalLayout searchBar;
    private TextField searchTxt;
    private Button searchButton;

    String fill140Char() {
        String oString = "Char140";
        for (int i = 0; i < 140; ++i) {
            oString += "a";
        }
        return oString;
    }

    public HomePageView() {
        //Scroll bar
        this.setSizeUndefined();

        //Vertical layout in teh center
        this.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        //Tabs to navigate
        home = new Tab("Home");
        profile = new Tab("Profile");
        logout = new Tab("Logout");
        home.getStyle().set("border", hexTurquoise);
        profile.getStyle().set("border", hexTurquoise);
        logout.getStyle().set("border", hexTurquoise);

        tabs = new Tabs(home, profile, logout);
        tabs.setFlexGrowForEnclosedTabs(1);

        add(tabs);

        //Search bar
        searchTxt = new TextField();
        searchTxt.setPlaceholder("Search");
        searchButton = new Button("Search");

        searchBar = new HorizontalLayout();
        searchBar.add(searchTxt, searchButton);
        searchBar.setPadding(false);
        searchBar.setMargin(true);
        searchBar.setSpacing(true);
        add(searchBar);

        //Tweets or posts
        String userName = "Username ";
        for (int i = 0; i < textAreaCounter; ++i) {
            userName += i;

            TextArea area = new TextArea();
            area.setReadOnly(true);
            area.setWidth("300px");
            area.setMaxLength(140 + userName.length());

            area.setValue(userName + "\n \n" + fill140Char());

            HorizontalLayout interactButtons = new HorizontalLayout();
            Button likeButton = new Button("Like");
            Button retweetButton = new Button("Retweet");
            Button commentButton = new Button("Comment");
            interactButtons.add(likeButton, retweetButton, commentButton);

            add(area, interactButtons);
        }
    }
}
