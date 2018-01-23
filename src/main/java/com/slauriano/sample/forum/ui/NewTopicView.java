package com.slauriano.sample.forum.ui;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.slauriano.sample.forum.model.Topic;
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
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringView(name = NewTopicView.VIEW_NAME)
public class NewTopicView extends CustomComponent implements  View{
	
	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_NAME = "new_topic";
	
	// Repositories to access data in database
	private final UserRepository userRepository;
	private final TopicRepository topicRepository;
	
	// components of the view
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	CssLayout actions = new CssLayout(save, cancel);
	private TextField title;
	private TextArea description;
	
	private Topic topic;
	
	@Autowired
	public NewTopicView(UserRepository userRepository, TopicRepository topicRepository) {
		
		this.userRepository = userRepository;
		this.topicRepository = topicRepository;
		
		VerticalLayout mainLayout = new VerticalLayout();
		
		// Fields to create a new Topic
		title = new TextField("Title");
		title.setWidth("80%");
		description = new TextArea("Description");
		description.setWidth("80%");
				
		// save button onclick event implementation		
		save.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				topic.setTitle(title.getValue());
				topic.setDescription(description.getValue());
				topic.setUser(userRepository.findById((Long)VaadinSession.getCurrent().getAttribute("userId")));
				topicRepository.save(topic);
				getUI().getNavigator().navigateTo(LandingPageView.VIEW_NAME);
			}
		});
		
		// cancel button onclick event implementation
		cancel.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(LandingPageView.VIEW_NAME);				
			}
		});
		
		// setting components into the main layout
		mainLayout.addComponents(title,description,actions);
		mainLayout.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(description, Alignment.MIDDLE_CENTER);
		mainLayout.setComponentAlignment(actions, Alignment.MIDDLE_CENTER);
		
		// set the main layout as the root layout
		setCompositionRoot(mainLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		topic = new Topic("","",new Date());

	}
}
