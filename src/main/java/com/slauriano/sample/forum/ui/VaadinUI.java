package com.slauriano.sample.forum.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.slauriano.sample.forum.repo.CommentRepository;
import com.slauriano.sample.forum.repo.TopicRepository;
import com.slauriano.sample.forum.repo.UserRepository;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI
@SpringViewDisplay
public class VaadinUI extends UI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private final UserRepository userRepository;
	
	@Autowired
	private final TopicRepository topicRepository;
	
	@Autowired
	private final CommentRepository commentRepository;
	
	@Autowired
	private final TopicEditor topicEditor;
	
	private Navigator navigator;
	
	VerticalLayout mainLayout;

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		
		mainLayout = new VerticalLayout();
		
		addHeaderLeabel();
		
		LoginView loginView = new LoginView(userRepository);
		
		mainLayout.addComponent(loginView);
		
		mainLayout.setSizeFull();
		
		mainLayout.setComponentAlignment(loginView, Alignment.MIDDLE_CENTER);
		
		setContent(mainLayout);
		
		LandingPageView landingPageView = new LandingPageView(userRepository, topicRepository,commentRepository,topicEditor);
		
		NewTopicView newTopicView = new NewTopicView(userRepository, topicRepository);
		
		// build navigator
		navigator = new Navigator(this, mainLayout);
		
		navigator.addView(loginView.VIEW_NAME, loginView);
		navigator.addView(landingPageView.VIEW_NAME, landingPageView);
		navigator.addView(newTopicView.VIEW_NAME, newTopicView);
		navigator.navigateTo(loginView.VIEW_NAME);
		
	}
	
	private void addHeaderLeabel(){
		HorizontalLayout header = new HorizontalLayout();
		
		Label headerLabel = new Label("Forum App");
		headerLabel.addStyleName(ValoTheme.LABEL_H1);
		
		header.setSizeFull();
		header.addComponent(headerLabel);
		
		mainLayout.addComponent(header);
	}
	
	@Autowired
	public VaadinUI(UserRepository userRepository, TopicRepository topicRepository, CommentRepository commentRepository,TopicEditor topicEditor) {
		this.userRepository = userRepository;
		this.topicRepository = topicRepository;
		this.commentRepository = commentRepository;
		this.topicEditor = topicEditor;
		 
	}
}
