package addresbookVaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;

import LoginVaadin.LoginMainView;
import LoginVaadin.LoginUI;
import LoginVaadin.LoginView;
import addresbookVaadin.backend.Contact;
import addresbookVaadin.backend.ContactService;

import javax.servlet.annotation.WebServlet;


@Title("Addressbook")
@Theme("valo")
public class AddressbookUI extends UI {

	public static final String NAME = "";
    TextField filter = new TextField();
    Grid contactList = new Grid();
    Button newContact = new Button("Nowy kontakt");
    Button logout = new Button("Logout", new Button.ClickListener() {

        @Override
        public void buttonClick(ClickEvent event) {

            // "Logout" the user
            getSession().setAttribute("user", null);
            LoginMainView.Logout(NAME);
            
        }
    });
   
    ContactForm contactForm = new ContactForm();

   
    ContactService service = ContactService.createDemoService();


   
    @Override
    protected void init(VaadinRequest request) {
        configureComponents();
        buildLayout();
    }


    private void configureComponents() {
        
        newContact.addClickListener(e -> contactForm.edit(new Contact()));

        filter.setInputPrompt("Filtr ksiazki adresowej ....");
        filter.addTextChangeListener(e -> refreshContacts(e.getText()));

        contactList.setContainerDataSource(new BeanItemContainer<>(Contact.class));
        contactList.setColumnOrder("firstName", "lastName","street","number","code", "email","phone","birthDate");
        contactList.removeColumn("id");

    
        contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
        contactList.addSelectionListener(e
                -> contactForm.edit((Contact) contactList.getSelectedRow()));
        refreshContacts();
        
       
       
    }

   
    private void buildLayout() {
        HorizontalLayout actions = new HorizontalLayout(filter, new CssLayout(newContact,logout));
        actions.setWidth("100%");
        filter.setWidth("100%");
        actions.setExpandRatio(filter, 1);
   

        VerticalLayout left = new VerticalLayout(actions, contactList);
        left.setSizeFull();
        contactList.setSizeFull();
        left.setExpandRatio(contactList, 1);

        HorizontalLayout mainLayout = new HorizontalLayout(left, contactForm);
        mainLayout.setSizeFull();
        mainLayout.setExpandRatio(left, 1);

        
        setContent(mainLayout);
    }

    
    void refreshContacts() {
        refreshContacts(filter.getValue());
    }

    private void refreshContacts(String stringFilter) {
        contactList.setContainerDataSource(new BeanItemContainer<>(
                Contact.class, service.findAll(stringFilter)));
        contactForm.setVisible(false);
    }


    @WebServlet(urlPatterns = {"/mainPage/*"}, name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = AddressbookUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    
}
