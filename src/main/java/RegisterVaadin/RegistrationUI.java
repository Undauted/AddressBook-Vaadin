package RegisterVaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import LoginVaadin.backend.Login;
import LoginVaadin.backend.LoginService;
import RegisterVaadin.RegistrationForm;
import RegisterVaadin.backend.Registration;
import RegisterVaadin.backend.RegistrationService;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

import LoginVaadin.LoginForm;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


@Title("Registration")
@Theme("valo")
public class RegistrationUI extends UI {
	 	
	TextField filter = new TextField();
    public Grid registrationList = new Grid();
    Button newRegistration = new Button("Rejestracja");
    Button newLogin = new Button("Logowanie");

    RegistrationForm registerForm = new RegistrationForm();

    RegistrationService serviceRegistration = new RegistrationService();

    @Override
    protected void init(VaadinRequest request) {
        configureComponentsRegister();
        buildLayout();
    }


    private void configureComponentsRegister() {
        
        newRegistration.addClickListener(e -> registerForm.edit(new Registration()));
        newRegistration.setStyleName(ValoTheme.BUTTON_PRIMARY);
        newRegistration.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        newRegistration.setHeight("100px");
        newRegistration.setWidth("200px");
        
        
        
        newLogin.setStyleName(ValoTheme.BUTTON_PRIMARY);
        newLogin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        newLogin.setHeight("100px");
        newLogin.setWidth("200px");
        
        newLogin.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
  
		    	Page.getCurrent().setLocation("/login/");

			}
		});

        refreshContacts();
    }

    private void buildLayout() {
        VerticalLayout actionsRegister = new VerticalLayout(newRegistration,newLogin);
        actionsRegister.setWidth("100%");
        actionsRegister.setHeight("100%");
        actionsRegister.addComponent(newRegistration);
        actionsRegister.addComponent(newLogin);
       
        actionsRegister.setComponentAlignment(newRegistration, Alignment.MIDDLE_CENTER);
        actionsRegister.setComponentAlignment(newLogin, Alignment.MIDDLE_CENTER);
        
        
        VerticalLayout leftRegister = new VerticalLayout(actionsRegister);
        leftRegister.setSizeFull();
        
        HorizontalLayout mainLayoutRegister = new HorizontalLayout(leftRegister, registerForm);
        mainLayoutRegister.setSizeFull();
        mainLayoutRegister.setExpandRatio(leftRegister, 1);

        setContent(mainLayoutRegister);
        

    }
    
    
    
    
    public void refreshContacts() {
        refreshContacts(filter.getValue());
    }

    private void refreshContacts(String stringFilter) {
        registrationList.setContainerDataSource(new BeanItemContainer<>(
                Registration.class, serviceRegistration.findAll(stringFilter)));
        registerForm.setVisible(false);
    }
	  


	@WebServlet(urlPatterns = {"/*", "/VAADIN/*"}, name = "MyRegistration", asyncSupported = true)
    @VaadinServletConfiguration(ui = RegistrationUI.class, productionMode = false)
    public static class MyRegistration extends VaadinServlet {
    }

	
}
