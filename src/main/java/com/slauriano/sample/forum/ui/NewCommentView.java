package com.slauriano.sample.forum.ui;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.slauriano.sample.forum.model.Comment;
import com.slauriano.sample.forum.repo.CommentRepository;
import com.slauriano.sample.forum.repo.TopicRepository;
import com.slauriano.sample.forum.repo.UserRepository;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = NewCommentView.VIEW_NAME)
public class NewCommentView extends CustomComponent implements  View{
	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_NAME = "new_comment";
	
	// Repositories to access data in database
	private final UserRepository userRepository;
	private final TopicRepository topicRepository;
	private final CommentRepository commentRepository;
		
	// components of the view
	private TextArea description;
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	CssLayout actions = new CssLayout(save, cancel);
	
	private Comment comment;
	
	@Autowired
	public NewCommentView(UserRepository userRepository, TopicRepository topicRepository,CommentRepository commentRepository) {
		this.userRepository = userRepository;
		this.topicRepository = topicRepository;
		this.commentRepository = commentRepository;
		
		VerticalLayout mainLayout = new VerticalLayout();
		
		// setup and size the component to show in the view
		description = new TextArea("Description");
		description.setWidth("80%");
		
		// save button onclick event implementation
		save.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				comment.setDescription(description.getValue());
				comment.setUser(userRepository.findById((Long)VaadinSession.getCurrent().getAttribute("userId")));
				comment.setTopic(topicRepository.findById((Long) VaadinSession.getCurrent().getAttribute("topicId")));
				commentRepository.save(comment);
				getUI().getNavigator().navigateTo(LandingPageView.VIEW_NAME);
			}
		});
		
		// cancel button onclick event implementation
		cancel.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(PageTopicView.VIEW_NAME);				
			}
		});
		
		// setting components into the main layout
		mainLayout.addComponents(description,actions);
		mainLayout.setComponentAlignment(description, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(actions, Alignment.MIDDLE_CENTER);
		
		// set the main layout as the root layout
		setCompositionRoot(mainLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		comment = new Comment("", new Date());
		
	}
}
