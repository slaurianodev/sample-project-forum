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

	// Repositories to access data in database
	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final TopicRepository topicRepository;
	@Autowired
	private final CommentRepository commentRepository;
	
	// navigator to switch the views
	private Navigator navigator;
	
	// main layout of the page
	VerticalLayout mainLayout;

	@Override
	protected void init(VaadinRequest request) {
		// build layout
		mainLayout = new VerticalLayout();
		
		// create and add the view to the main layout
		LoginView loginView = new LoginView(userRepository);
		
		mainLayout.addComponent(loginView);
		
		mainLayout.setComponentAlignment(loginView, Alignment.MIDDLE_CENTER);
		
		setContent(mainLayout);
		
		// creating other views to use in the navigator in the future
		LandingPageView landingPageView = new LandingPageView(userRepository, topicRepository,commentRepository);
		NewTopicView newTopicView = new NewTopicView(userRepository, topicRepository);
		
		// build navigator
		navigator = new Navigator(this, mainLayout);
		
		navigator.addView(loginView.VIEW_NAME, loginView);
		navigator.addView(landingPageView.VIEW_NAME, landingPageView);
		navigator.addView(newTopicView.VIEW_NAME, newTopicView);
		navigator.navigateTo(loginView.VIEW_NAME);
		
	}
	
	@Autowired
	public VaadinUI(UserRepository userRepository, TopicRepository topicRepository, CommentRepository commentRepository) {
		this.userRepository = userRepository;
		this.topicRepository = topicRepository;
		this.commentRepository = commentRepository;		 
	}
}
