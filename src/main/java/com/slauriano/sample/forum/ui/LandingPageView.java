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

@SpringView(name = LandingPageView.VIEW_NAME)
public class LandingPageView extends CustomComponent implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "home";

	final Grid<Topic> grid;

	final TextField filter;

	private final Button addNewBtn;

	private final UserRepository userRepository;

	private final TopicRepository topicRepository;

	private final CommentRepository commentRepository;

	private final TopicEditor editor;

	private Navigator navigator;
	
	private final Button logout;

	@Autowired
	public LandingPageView(UserRepository userRepository, TopicRepository topicRepository,
			CommentRepository commentRepository, TopicEditor editor) {

		this.userRepository = userRepository;
		this.topicRepository = topicRepository;
		this.commentRepository = commentRepository;
		this.editor = editor;

		this.grid = new Grid<>(Topic.class);
		this.filter = new TextField();
		this.addNewBtn = new Button("New topic", FontAwesome.PLUS);
		this.logout = new Button("Logout");

		// build layout
		HorizontalLayout logoutAction = new HorizontalLayout(logout);
		
		HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(logoutAction,actions, grid, editor);
		mainLayout.setComponentAlignment(logoutAction, Alignment.TOP_RIGHT);

		grid.setHeightByRows(10);
		grid.setWidth("80%");
		grid.setColumns("title", "creationDate");

		grid.asSingleSelect().addValueChangeListener(e -> {

			PageTopicView pageTopicView = new PageTopicView(userRepository, topicRepository, commentRepository,
					e.getValue());

			navigator.addView(pageTopicView.VIEW_NAME, pageTopicView);
			
			getUI().getNavigator().navigateTo(PageTopicView.VIEW_NAME);

		});

		filter.setPlaceholder("Filter by topic title");

		filter.setValueChangeMode(ValueChangeMode.LAZY);
		filter.addValueChangeListener(e -> listTopics(e.getValue()));
		filter.setWidth("50%");

		addNewBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(NewTopicView.VIEW_NAME);
			}
		});
		
		logout.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(LoginView.VIEW_NAME);				
			}
		});

		setCompositionRoot(mainLayout);

		listTopics(null);

	}

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
