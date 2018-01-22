package com.slauriano.sample.forum.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.slauriano.sample.forum.model.Comment;
import com.slauriano.sample.forum.model.Topic;
import com.slauriano.sample.forum.repo.CommentRepository;
import com.slauriano.sample.forum.repo.TopicRepository;
import com.slauriano.sample.forum.repo.UserRepository;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = PageTopicView.VIEW_NAME)
public class PageTopicView extends CustomComponent implements View {
	private static final long serialVersionUID = 1L;

	public static final String VIEW_NAME = "topic_page";

	private final UserRepository userRepository;

	private final TopicRepository topicRepository;

	private final CommentRepository commentRepository;

	private Topic topic;

	private Navigator navigator;

	@Autowired
	public PageTopicView(UserRepository userRepository, TopicRepository topicRepository,
			CommentRepository commentRepository, Topic topic) {
		this.userRepository = userRepository;
		this.topicRepository = topicRepository;
		this.commentRepository = commentRepository;
		
		this.addStyleName("v-scrollable");
		this.setHeight("100%");
		this.setWidth("100%");
		
		VerticalLayout mainLayout = new VerticalLayout();

		getTopic(topic);

		Label titleTopic = new Label(topic.getTitle());
		titleTopic.addStyleName(ValoTheme.LABEL_H1);

		Label descriptionTopic = new Label(topic.getDescription());
		Label dateTopic = new Label("at " + topic.getCreationDate());

		Button backButton = new Button("Back");
		Button newCommentBtn = new Button("New comment", FontAwesome.PLUS);
		HorizontalLayout actions = new HorizontalLayout(newCommentBtn, backButton);

		GridLayout gridTopicLayout = new GridLayout();
		gridTopicLayout.setMargin(true);
		gridTopicLayout.setWidth("80%");

		gridTopicLayout.addComponent(titleTopic);
		gridTopicLayout.addComponent(descriptionTopic);
		gridTopicLayout.addComponent(dateTopic);

		GridLayout gridCommentLayout = new GridLayout();

		getComments(gridCommentLayout, topic);

		backButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(LandingPageView.VIEW_NAME);
			}
		});

		newCommentBtn.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				VaadinSession.getCurrent().setAttribute("topicId", topic.getId());

				NewCommentView newCommentView = new NewCommentView(userRepository, topicRepository, commentRepository);
				navigator.addView(newCommentView.VIEW_NAME, newCommentView);

				getUI().getNavigator().navigateTo(NewCommentView.VIEW_NAME);
			}
		});

		mainLayout.addComponent(actions);
		mainLayout.setComponentAlignment(actions, Alignment.BOTTOM_RIGHT);
		mainLayout.addComponent(gridTopicLayout);
		mainLayout.addComponent(gridCommentLayout);

		setCompositionRoot(mainLayout);

	}

	private String formatDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return dateFormat.format(date);
	}

	public void getTopic(Topic topicSelected) {
		final boolean persisted = topicSelected.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			topic = topicRepository.findOne(topicSelected.getId());
		}
	}

	void getComments(GridLayout gridLayout, Topic topic) {
		List<Comment> comments = commentRepository.findCommentsByTopic(topic);
		for (Comment c : comments) {
			Label lineSeparator1 = new Label("------------------------------");
			Label lineSeparator2 = new Label("------------------------------");
			Label commentDescription = new Label(c.getDescription());
			Label commentDateAndOwner = new Label(String.format("at %s by %s", formatDate(c.getCreationDate()),c.getUser().getUserName()));

			gridLayout.addComponents(lineSeparator1,commentDescription, lineSeparator2,commentDateAndOwner);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		navigator = event.getNavigator();
	}
}
