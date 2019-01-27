package com.basilisk.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("Home")
public class HomePageView extends VerticalLayout {
    Integer textAreaCounter = 5;
    String hexTurquoise = "#40E0D0";

    public HomePageView() {
        //Tabs to navigate
        Tab home = new Tab("Home");
        Tab profile = new Tab("Profile");
        home.getStyle().set("border", hexTurquoise);
        profile.getStyle().set("border", hexTurquoise);
        Tabs tabs = new Tabs(home, profile);
        tabs.setFlexGrowForEnclosedTabs(1);
        add(tabs);

        this.getStyle().set("border", hexTurquoise);
        this.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        //Search bar
        TextField textField = new TextField();
        textField.setLabel("Search");
        Button searchBar = new Button("Search");

        add(textField, searchBar);

        //Messages or tweets
        this.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        for (int i = 0; i < textAreaCounter; ++i) {
            TextArea area = new TextArea("Username");
            //area.setWordWrap(true);
            area.setValue("Message");

            add(area);
            ++i;
        }
    }
}
