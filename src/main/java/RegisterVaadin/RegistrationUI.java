package RegisterVaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

import RegisterVaadin.RegistrationForm;
import RegisterVaadin.backend.Registration;
import RegisterVaadin.backend.RegistrationService;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


@Title("Registration")
@Theme("valo")
public class RegistrationUI extends UI {
	 	
	TextField filter = new TextField();
    Grid registrationList = new Grid();
    Button newContact = new Button("Nowy kontakt");

   
    RegistrationForm registerForm = new RegistrationForm();

   
    RegistrationService service = RegistrationService.createDemoService();


   
    @Override
    protected void init(VaadinRequest request) {
        configureComponents();
        buildLayout();
    }


    private void configureComponents() {
        
        newContact.addClickListener(e -> registerForm.edit(new Registration()));

        filter.setInputPrompt("Filtr ksiazki adresowej ....");
        filter.addTextChangeListener(e -> refreshContacts(e.getText()));

        registrationList.setContainerDataSource(new BeanItemContainer<>(Registration.class));
        registrationList.setColumnOrder("username", "password","confirmPassword","email");
        registrationList.removeColumn("id");

    
        registrationList.setSelectionMode(Grid.SelectionMode.SINGLE);
        registrationList.addSelectionListener(e
                -> registerForm.edit((Registration) registrationList.getSelectedRow()));
        refreshContacts();
    }

   
    private void buildLayout() {
        HorizontalLayout actions = new HorizontalLayout(filter, newContact);
        actions.setWidth("100%");
        filter.setWidth("100%");
        actions.setExpandRatio(filter, 1);

        VerticalLayout left = new VerticalLayout(actions, registrationList);
        left.setSizeFull();
        registrationList.setSizeFull();
        left.setExpandRatio(registrationList, 1);

        HorizontalLayout mainLayout = new HorizontalLayout(left, registerForm);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(left, 1);

        
        setContent(mainLayout);
    }

    
    void refreshContacts() {
        refreshContacts(filter.getValue());
    }

    private void refreshContacts(String stringFilter) {
        registrationList.setContainerDataSource(new BeanItemContainer<>(
                Registration.class, service.findAll(stringFilter)));
        registerForm.setVisible(false);
    }
	  


	@WebServlet(urlPatterns = {"/register/*", "/VAADIN/*"}, name = "MyRegistration", asyncSupported = true)
    @VaadinServletConfiguration(ui = RegistrationUI.class, productionMode = false)
    public static class MyRegistration extends VaadinServlet {
    }

	
}
