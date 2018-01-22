package com.slauriano.sample.forum.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.slauriano.sample.forum.model.Topic;
import com.slauriano.sample.forum.repo.TopicRepository;
import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("deprecation")
@SpringComponent
@UIScope
public class TopicEditor extends VerticalLayout {

	private final TopicRepository repository;

	/**
	 * The currently edited customer
	 */
	private Topic topic;

	/* Fields to edit properties in Customer entity */
	TextField title = new TextField("Title");
	TextArea description = new TextArea("Description");

	/* Action buttons */
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel, delete);

	Binder<Topic> binder = new Binder<>(Topic.class);

	@Autowired
	public TopicEditor(TopicRepository repository) {
		this.repository = repository;

		addComponents(title, description, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		actions.setSizeUndefined();
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> repository.save(topic));
		delete.addClickListener(e -> repository.delete(topic));
		cancel.addClickListener(e -> editTopic(topic));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editTopic(Topic topicEdit) {
		if (topicEdit == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = topicEdit.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			topic = repository.findOne(topicEdit.getId());
		}
		else {
			topic = topicEdit;
		}
		cancel.setVisible(persisted);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(topic);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		title.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}