package com.basilisk.frontend.views;

import com.basilisk.Constants;
import com.basilisk.backend.models.User;
import com.basilisk.backend.presenters.ProfilePresenter;
import com.basilisk.backend.presenters.TweetPresenter;
import com.basilisk.frontend.components.MenuBarComponent;
import com.basilisk.frontend.components.TweetCreateComponent;
import com.basilisk.frontend.components.TweetDisplayComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.polymertemplate.EventHandler;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.List;
import java.util.Objects;

@Tag("profile-view")
@HtmlImport("profile-view.html")
@Route("profile")
@Uses(MenuBarComponent.class)
public class ProfileView extends PolymerTemplate<ProfileView.ProfileViewModel> implements BeforeEnterObserver, HasUrlParameter<String> {

    private static final String FOLLOW_USER = "Follow User";
    private static final String UNFOLLOW_USER = "Unfollow User";
    private static final String UPLOAD_PROFILE_IMAGE = "Upload Photo";
    private static final String UPLOAD_COVER_IMAGE = "Upload Cover";

    private ProfilePresenter profilePresenter;
    private TweetPresenter tweetPresenter;
    private String usernameParameter;

    @Id("tweetFeed")
    private Element tweetFeed;

    @Id("followButton")
    private Button followButton;

    @Id("userNameLabel")
    private Label userNameLabel;

    @Id("followersLabel")
    private Label followersLabel;

    @Id("followingLabel")
    private Label followingLabel;

    @Id("userBioTextArea")
    private TextArea userBioTextArea;

    @Id("editButton")
    private Button editButton;

    @Id("profileImageUpload")
    private Upload profileImageUpload;

    @Id("coverImageUpload")
    private Upload coverImageUpload;

    @Id("profileImage")
    private Image profileImage;

    @Id("coverImage")
    private Image coverImage;

    public ProfileView(ProfilePresenter profilePresenter, TweetPresenter tweetPresenter) {
        this.profilePresenter = profilePresenter;
        this.tweetPresenter = tweetPresenter;
    }

    public void init() {
        User currentUser = (User) VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER);
        VaadinSession.getCurrent().setAttribute("currentPage", "profile");

        if (Objects.isNull(usernameParameter)) { //If route is just /profile then go to currentUser page
            usernameParameter = currentUser.getUsername();
            UI.getCurrent().navigate(Constants.PROFILE_ROUTE + usernameParameter);
        } else { //Else the route will be /profile/someUser
            User userProfile = profilePresenter.getUser(usernameParameter);
            if (Objects.isNull(userProfile)) {
                //TODO: Error page if user doesn't exist
                UI.getCurrent().navigate(Constants.PROFILE_ROUTE + currentUser.getUsername());
            }
            VaadinSession.getCurrent().setAttribute(Constants.USER_PROFILE, userProfile);
        }

        //Remove all children from tweetFeed so no duplicates
        tweetFeed.removeAllChildren();

        //Loading the tweets
        User userProfile = (User) VaadinSession.getCurrent().getAttribute(Constants.USER_PROFILE);
        List<TweetDisplayComponent> tweetDisplayComponentList = profilePresenter.getAllUserTweetDisplayComponents(userProfile);

        //Setting up stats
        userNameLabel.getElement().setText(userProfile.getName() + " @" + userProfile.getUsername());
        followingLabel.getElement().setText("Following: " + profilePresenter.getNumberOfFollowings(userProfile));
        followersLabel.getElement().setText("Followers: " + profilePresenter.getNumberOfFollowers(userProfile));

        //Setting up bio
        userBioTextArea.setReadOnly(true);
        userBioTextArea.setValue(userProfile.getBiography());

        //If not user profile set edit buttons to invisible
        editButton.setVisible(false);
        profileImageUpload.setVisible(false);
        coverImageUpload.setVisible(false);

        //Setting up profile image
        profilePresenter.setImages(profileImage, coverImage, userProfile);

        //followButton Login for when page is initalized if its follow or unfollow user
        followButton.setVisible(true);
        if (profilePresenter.checkIfFollowing(currentUser, userProfile)) {
            followButton.setText(UNFOLLOW_USER);
        } else {
            followButton.setText(FOLLOW_USER);
        }

        //Things you can see on only your own page
        if (userProfile.equals(currentUser)) {
            tweetFeed.appendChild(new TweetCreateComponent(tweetPresenter).getElement());
            followButton.setVisible(false);
            editButton.setVisible(true);
            profileImageUpload.setVisible(true);
            coverImageUpload.setVisible(true);
        }

        //Profile Image and Cover Image Initalizations
        MemoryBuffer profileImageBuffer = new MemoryBuffer();
        MemoryBuffer coverImageBuffer = new MemoryBuffer();
        profileImageUpload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        coverImageUpload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        profileImageUpload.setUploadButton(new Button(UPLOAD_PROFILE_IMAGE));
        coverImageUpload.setUploadButton(new Button(UPLOAD_COVER_IMAGE));
        profileImageUpload.setDropAllowed(false);
        coverImageUpload.setDropAllowed(false);
        profileImageUpload.setReceiver(profileImageBuffer);
        coverImageUpload.setReceiver(coverImageBuffer);
        profileImageUpload.setMaxFiles(1);
        coverImageUpload.setMaxFiles(1);

        //Adding tweets to div element (tweetFeed)
        for (TweetDisplayComponent tweetDisplayComponent : tweetDisplayComponentList) {
            tweetFeed.appendChild(tweetDisplayComponent.getElement());
        }

        //When Profile Image is Uploaded Listener
        profileImageUpload.addSucceededListener(event -> {
            profilePresenter.uploadProfileImage(profileImageBuffer.getInputStream(), currentUser);
            UI.getCurrent().getPage().reload();
        });

        //When Cover Image is Uploaded Listener
        coverImageUpload.addSucceededListener(event -> {
            profilePresenter.uploadCoverImage(coverImageBuffer.getInputStream(), currentUser);
            UI.getCurrent().getPage().reload();
        });
    }

    @EventHandler
    private void followButtonClicked() {
        User currentUser = (User) VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER);
        User userProfile = (User) VaadinSession.getCurrent().getAttribute(Constants.USER_PROFILE);

        if (profilePresenter.checkIfFollowing(currentUser, userProfile)) {
            followButton.setText(FOLLOW_USER);
            profilePresenter.unfollowUser(currentUser, userProfile);
            followersLabel.getElement().setText("Followers: " + profilePresenter.getNumberOfFollowers(userProfile));
        } else {
            followButton.setText(UNFOLLOW_USER);
            profilePresenter.followUser(currentUser, userProfile);
            followersLabel.getElement().setText("Followers: " + profilePresenter.getNumberOfFollowers(userProfile));
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        VaadinSession vaadinSession = VaadinSession.getCurrent();
        if (Objects.isNull(vaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER))) {
            beforeEnterEvent.rerouteTo(LoginView.class);
            UI.getCurrent().navigate("");
        }
        init();
    }

    @EventHandler
    public void editButtonClicked() {
        User currentUser = (User) VaadinSession.getCurrent().getAttribute(Constants.CURRENT_USER);

        if (userBioTextArea.isReadOnly()) {
            //Edit Mode
            editButton.setText("Save");
            userBioTextArea.setReadOnly(false);
        } else {
            //Save Mode
            editButton.setText("Edit");
            userBioTextArea.setReadOnly(true);
            currentUser.setBiography(userBioTextArea.getValue());
            profilePresenter.saveBiography(currentUser);

        }
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        usernameParameter = parameter;
    }

    public interface ProfileViewModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
