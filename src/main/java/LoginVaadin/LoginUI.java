package LoginVaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;


import LoginVaadin.LoginForm;
import LoginVaadin.backend.Login;
import LoginVaadin.backend.LoginService;

@Title("Login")
@Theme("valo")
public class LoginUI extends UI {

	TextField filter = new TextField();
    public Grid registrationList = new Grid();
    Button newLogin = new Button("Logowanie");

   
    LoginForm loginForm = new LoginForm();

    LoginService serviceLogin = new LoginService();

   
    @Override
    protected void init(VaadinRequest request) {
        configureComponentsRegister();
        buildLayout();
    }


    private void configureComponentsRegister() {
        
    	 newLogin.addClickListener(e -> loginForm.edit(new Login()));
         newLogin.setStyleName(ValoTheme.BUTTON_PRIMARY);
         newLogin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
         newLogin.setHeight("100px");
         newLogin.setWidth("200px");
        
        refreshContacts();
    }

    private void buildLayout() {
        HorizontalLayout actionsRegister = new HorizontalLayout(newLogin);
        actionsRegister.setWidth("100%");
        actionsRegister.setHeight("70%");
        actionsRegister.addComponent(newLogin);
        actionsRegister.setComponentAlignment(newLogin, Alignment.MIDDLE_CENTER);

        
        VerticalLayout leftRegister = new VerticalLayout(actionsRegister);
        leftRegister.setSizeFull();
        
        HorizontalLayout mainLayoutRegister = new HorizontalLayout(leftRegister, loginForm);
        mainLayoutRegister.setSizeFull();
        mainLayoutRegister.setExpandRatio(leftRegister, 1);

        setContent(mainLayoutRegister);
        

    }
    
    

    
    public void refreshContacts() {
        refreshContacts(filter.getValue());
    }

    private void refreshContacts(String stringFilter) {
        registrationList.setContainerDataSource(new BeanItemContainer<>(
                Login.class, serviceLogin.findAll(stringFilter)));
        loginForm.setVisible(false);
    }
	  


	@WebServlet(urlPatterns = {"/login/*"}, name = "MyLogin", asyncSupported = true)
    @VaadinServletConfiguration(ui = LoginUI.class, productionMode = false)
    public static class MyLogin extends VaadinServlet {
    }
	
}
