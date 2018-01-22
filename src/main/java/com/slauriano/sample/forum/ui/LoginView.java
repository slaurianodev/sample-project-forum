package com.slauriano.sample.forum.ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.slauriano.sample.forum.model.User;
import com.slauriano.sample.forum.repo.UserRepository;
import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

@SpringView(name = LoginView.VIEW_NAME)
public class LoginView extends CustomComponent implements View{
	
	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_NAME = "login";

	private final UserRepository userRepository;
	
	public User user;
	
	TextField userName = new TextField("username");
	PasswordField password = new PasswordField("password");
	Button login = new Button("Login");
	
	Binder<User> binder = new Binder<User>();
	
	
	@Autowired
	public LoginView(UserRepository userRepository) {
		this.userRepository = userRepository;
		
		// Create a panel with a caption.
		Panel loginPanel = new Panel("Login");
		loginPanel.setSizeUndefined();
		loginPanel.setWidth(null);
		
		// Create a layout inside the panel
		final FormLayout loginLayout = new FormLayout();

		// Add some components inside the layout
		loginLayout.addComponent(userName);
		loginLayout.addComponent(password);
		loginLayout.addComponent(login);
		setSizeFull();

		// Have some margin around it.
		loginLayout.setMargin(true);
		
		// set required indicator for text fields
		userName.setRequiredIndicatorVisible(true);
		password.setRequiredIndicatorVisible(true);
		
		
		// Set the layout as the root layout of the panel
		loginPanel.setContent(loginLayout);
		
		setCompositionRoot(loginPanel);
				

		// login button onclick event implementation
		login.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String userNameVal = userName.getValue();
				String passVal = password.getValue();
				
				// recover the user data from database
				User loggedUser = userRepository.findByUserNameAndPassword(userNameVal, passVal);

				if(loggedUser != null) {
					// save the id of the current user logged in the app
					VaadinSession.getCurrent().setAttribute("userId", loggedUser.getId());
					getUI().getNavigator().navigateTo(LandingPageView.VIEW_NAME);					
				}
			}
		});
		
	}

	@Override
	public void enter(ViewChangeEvent event) {}

}
