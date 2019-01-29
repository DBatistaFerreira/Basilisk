package com.basilisk.frontend.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

@Route("Profile")
public class ProfilePageView extends VerticalLayout {

    Integer textAreaCounter = 10;
    String hexTurquoise = "#40E0D0";

    private HorizontalLayout navigationBar;
    private Tab home;
    private Tab profile;
    private Tabs tabs;

    private Button logout;
    private Button reload;

    private Image coverPic;
    private Image profilePic;

    //User profile variable
    private String bioName;
    private String bioDescription;
    private Integer numberOfFollowers;
    private Integer numberOfFollowing;

    private String fill140Char() {
        String oString = "Char140";
        for (int i = 0; i < 140; ++i) {
            oString += "a";
        }
        return oString;
    }

    public ProfilePageView() {

        //Scroll bar
        this.setSizeUndefined();

        //Vertical layout in the center
        this.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        //Tabs and buttons to navigate
        navigationBar = new HorizontalLayout();

        home = new Tab("Home");
        profile = new Tab("Profile");
        home.getStyle().set("border", hexTurquoise);
        profile.getStyle().set("border", hexTurquoise);

        tabs = new Tabs(home, profile);
        tabs.setFlexGrowForEnclosedTabs(1);
        tabs.setSelectedIndex(1);

        logout = new Button("Logout");
        reload = new Button("Reload");
        logout.getStyle().set("border", hexTurquoise);
        reload.getStyle().set("border", hexTurquoise);

        navigationBar.add(tabs, logout, reload);
        navigationBar.setPadding(false);
        navigationBar.setMargin(true);
        navigationBar.setSpacing(true);
        add(navigationBar);

        //Cover picture settings
        coverPic = new Image("http://www.superwallpapers.in/hdwallpapers/boston-red-sox-cover.jpg", "cover picture 850x315");
        coverPic.getStyle().set("z-index", "-1");
        coverPic.getStyle().set("position", "relative");

        //Profile picture settings
        profilePic = new Image("https://scontent.fymy1-2.fna.fbcdn.net/v/t1.0-9/41321468_1823723824370053_2470938163342737408_n.jpg?_nc_cat=105&_nc_ht=scontent.fymy1-2.fna&oh=963ce263f4950878e8aeb7c093bb5eac&oe=5CF91A99", "profile picture 360x360");
        profilePic.getStyle().set("position", "relative");
        profilePic.setWidth("180px");
        profilePic.setHeight("180px");
        profilePic.getStyle().set("bottom", "130px");
        profilePic.getStyle().set("right", "450px");
        profilePic.getStyle().set("clip-path", "polygon(30% 0%, 70% 0%, 100% 30%, 100% 70%, 70% 100%, 30% 100%, 0% 70%, 0% 30%)");

        //Adding pictures to layout
        add(coverPic);
        add(profilePic);

        //default user values for sake of prototype
        bioName = "LBuzz1995";
        bioDescription = "My mom says I'm funny.";
        numberOfFollowers = 35;
        numberOfFollowing = 45;

        //Main container for both bio info and bio "about me" section for style purposes
        Div bio = new Div();

        //Information containing username, followers, and following
        TextArea bioInfo = new TextArea("");
        bioInfo.setValue(bioName + " | | Followers " + numberOfFollowers + " | | Following " + numberOfFollowing);
        bioInfo.setReadOnly(true);
        bioInfo.setWidth("500px");
        bioInfo.getStyle().set("position", "relative");
        bioInfo.getStyle().set("left", "109px");
        bioInfo.getStyle().set("bottom", "220px");

        //Information containing description of user
        TextArea bioAboutMe = new TextArea("About me");
        bioAboutMe.setValue(bioDescription);
        bioAboutMe.setReadOnly(true);
        bioAboutMe.setWidth("300px");
        bioAboutMe.getStyle().set("position", "relative");
        bioAboutMe.getStyle().set("right", "680px");
        bioAboutMe.getStyle().set("bottom", "135px");
        bioAboutMe.setWidth("220px");

        //adding both together into main container
        bio.add(bioInfo, bioAboutMe);

        //adding container to the main layout
        add(bio);

        //Tweets or posts
        String userName = "Username ";
        for (int i = 0; i < textAreaCounter; ++i) {
            userName += i;

            TextArea area = new TextArea();
            area.setReadOnly(true);
            area.setWidth("300px");
            area.setMaxLength(140 + bioName.length());

            area.setValue(bioName + "\n \n" + fill140Char());

            HorizontalLayout interactButtons = new HorizontalLayout();
            Button likeButton = new Button("Like");
            Button retweetButton = new Button("Retweet");
            Button commentButton = new Button("Comment");
            interactButtons.add(likeButton, retweetButton, commentButton);
            area.getStyle().set("position", "relative");
            interactButtons.getStyle().set("position", "relative");
            area.getStyle().set("bottom", "190px");
            interactButtons.getStyle().set("bottom", "190px");
            add(area, interactButtons);
        }
    }
}

