package com.slauriano.sample.forum.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.slauriano.sample.forum.model.Topic;
import com.slauriano.sample.forum.repo.CommentRepository;
import com.slauriano.sample.forum.repo.TopicRepository;
import com.slauriano.sample.forum.repo.UserRepository;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
/**
 * Class of the main page of the app 
 * 
 * @author slauriano
 * 
 */
@SpringView(name = LandingPageView.VIEW_NAME)
public class LandingPageView extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "home";

	// components of the view
	final Grid<Topic> grid;
	final TextField filter;
	private final Button addNewBtn;
	private final Button logout;
	
	// Repositories to access data in database
	private final UserRepository userRepository;
	private final TopicRepository topicRepository;
	private final CommentRepository commentRepository;
	
	// navigator to switch the views
	private Navigator navigator;

	@Autowired
	public LandingPageView(UserRepository userRepository, TopicRepository topicRepository,
			CommentRepository commentRepository) {

		this.userRepository = userRepository;
		this.topicRepository = topicRepository;
		this.commentRepository = commentRepository;

		// Initialize the grid to show the topic data
		this.grid = new Grid<>(Topic.class);
		
		// Initialize the components with actions in the page
		this.filter = new TextField();
		this.addNewBtn = new Button("New topic", FontAwesome.PLUS);
		this.logout = new Button("Logout");

		// build layout of the actions
		HorizontalLayout logoutAction = new HorizontalLayout(logout);
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		
		// build the main layout and set the components into
		VerticalLayout mainLayout = new VerticalLayout(logoutAction,actions, grid);
		mainLayout.setComponentAlignment(logoutAction, Alignment.TOP_RIGHT);

		// fill settings of the grid component in the main layout
		grid.setHeightByRows(10);
		grid.setWidth("80%");
		grid.setColumns("title", "creationDate");

		// item selected onclick event implementation
		grid.asSingleSelect().addValueChangeListener(e -> {

			PageTopicView pageTopicView = new PageTopicView(userRepository, topicRepository, commentRepository,
					e.getValue());

			navigator.addView(pageTopicView.VIEW_NAME, pageTopicView);
			
			getUI().getNavigator().navigateTo(PageTopicView.VIEW_NAME);

		});

		// fill settings of the filter component in the main layout
		filter.setPlaceholder("Filter by topic title");
		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listTopics(e.getValue()));
		filter.setWidth("50%");

		// new button onclick event implementation
		addNewBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(NewTopicView.VIEW_NAME);
			}
		});
		
		// logout button on click event implementation
		logout.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(LoginView.VIEW_NAME);				
			}
		});

		// set the main layout as the root layout
		setCompositionRoot(mainLayout);

		// list all topics to fill the grid		
		listTopics(null);

	}

	/**
	 * List topics to fill the grid
	 * 
	 * @param filterText - the parameter to be used in the query
	 * 
	 * @author slauriano
	 * 
	 * */	
	void listTopics(String filterText) {
		if (StringUtils.isEmpty(filterText)) {
			grid.setItems(topicRepository.findAll());

		} else {
			grid.setItems(topicRepository.findByTitle(filterText));
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		listTopics(null);
		navigator = event.getNavigator();
	}

}
