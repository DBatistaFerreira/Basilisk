package com.basilisk.frontend.components;

import com.basilisk.Constants;
import com.basilisk.backend.models.Comment;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.io.ByteArrayInputStream;
import java.util.Objects;

@Tag("comment-display-component")
@HtmlImport("comment-display-component.html")
public class CommentDisplayComponent extends PolymerTemplate<CommentDisplayComponent.CommentDisplayComponentModel> {

    @Id("UserLink")
    private Anchor UserLink;
    @Id("profileImage")
    private Image profileImage;
    @Id("UserComment")
    private TextArea UserComment;

    public CommentDisplayComponent(Comment iComment) {
        byte[] profileImageData = iComment.getUser().getProfilePicture();
        if (Objects.isNull(profileImageData)) {
            profileImage.setSrc("frontend/defaultProfileImage.jpg");
        } else {
            StreamResource profilePictureResource = new StreamResource(iComment.getUser().getUsername() + ".jpg", () -> new ByteArrayInputStream(profileImageData));
            profileImage.setSrc(profilePictureResource);
        }

        UserLink.setText(iComment.getUser().getName() + " (@" + iComment.getUser().getUsername() + ")");
        UserLink.setHref(Constants.PROFILE_ROUTE + iComment.getUser().getUsername());

        UserComment.setValue(iComment.getText() + "\n");
    }

    public interface CommentDisplayComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
