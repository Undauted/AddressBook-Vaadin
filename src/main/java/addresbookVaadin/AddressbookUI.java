package addresbookVaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import addresbookVaadin.backend.Contact;
import addresbookVaadin.backend.ContactService;

import javax.servlet.annotation.WebServlet;


@Title("Addressbook")
@Theme("valo")
public class AddressbookUI extends UI {

	
    TextField filter = new TextField();
    Grid contactList = new Grid();
    Button newContact = new Button("Nowy kontakt");

   
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
        contactList.setColumnOrder("firstName", "lastName", "email","phone","birthDate");
        contactList.removeColumn("id");

    
        contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
        contactList.addSelectionListener(e
                -> contactForm.edit((Contact) contactList.getSelectedRow()));
        refreshContacts();
    }

   
    private void buildLayout() {
        HorizontalLayout actions = new HorizontalLayout(filter, newContact);
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


    @WebServlet(urlPatterns = "/*")
    @VaadinServletConfiguration(ui = AddressbookUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }


}
