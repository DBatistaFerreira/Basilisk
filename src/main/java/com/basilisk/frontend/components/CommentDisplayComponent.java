package com.basilisk.frontend.components;

import com.basilisk.Constants;
import com.basilisk.backend.models.Comment;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.templatemodel.TemplateModel;

import java.util.List;

@Tag("comment-display-component")
@HtmlImport("comment-display-component.html")
public class CommentDisplayComponent extends PolymerTemplate<CommentDisplayComponent.CommentDisplayComponentModel> {

    @Id("UserComment")
    private TextArea UserComment;

    public CommentDisplayComponent(Comment iComment) {
        UserComment.setValue(iComment.getUser().getUsername() + ": " + iComment.getText() + "\n");
    }

    public interface CommentDisplayComponentModel extends TemplateModel {
        // Add setters and getters for template properties here.
    }
}
