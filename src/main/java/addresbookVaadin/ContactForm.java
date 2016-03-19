package addresbookVaadin;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import addresbookVaadin.backend.Contact;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;


public class ContactForm extends FormLayout {

    Button save = new Button("Zapisz", this::save);
    Button delete = new Button("Usun", this::delete);
    Button cancel = new Button("Cofnij", this::cancel);
    TextField firstName = new TextField("Imie");
    TextField lastName = new TextField("Nazwisko");
    TextField phone = new TextField("Telefon");
    TextField email = new TextField("Email");
    DateField birthDate = new DateField("Data urodzenia");

  
    Contact contact;
    
    
    
    
    BeanFieldGroup<Contact> formFieldBindings;

    public ContactForm() {
        configureComponents();
        buildLayout();
    }

    private void configureComponents() {
        
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        setVisible(false);
    }

    private void buildLayout() {
        setSizeUndefined();
        setMargin(true);

        HorizontalLayout actions = new HorizontalLayout(save,delete, cancel);
        actions.setSpacing(true);

		addComponents(actions, firstName, lastName, phone, email, birthDate);
    }

    
    public void save(Button.ClickEvent event) {
        try {
          
            formFieldBindings.commit();

            
            getUI().service.save(contact);

            String msg = String.format("Zapisany '%s %s'.",
                    contact.getFirstName(),
                    contact.getLastName());
            Notification.show(msg,Type.TRAY_NOTIFICATION);
            getUI().refreshContacts();
        } catch (FieldGroup.CommitException e) {
           
        }
    }

    public void cancel(Button.ClickEvent event) {
        
        Notification.show("Cofnieto", Type.TRAY_NOTIFICATION);
        getUI().contactList.select(null);
    }

    
    public void delete(Button.ClickEvent event) {
        try {
            
            formFieldBindings.commit();

           
            getUI().service.delete(contact);

            String msg = String.format("Usunieto '%s %s'.",
                    contact.getFirstName(),
                    contact.getLastName());
            Notification.show(msg,Type.TRAY_NOTIFICATION);
            getUI().refreshContacts();
        } catch (FieldGroup.CommitException e) {
            
        }
    }
    
    void edit(Contact contact) {
        this.contact = contact;
        if(contact != null) {
           
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(contact, this);
            firstName.focus();
        }
        setVisible(contact != null);
    }

    @Override
    public AddressbookUI getUI() {
        return (AddressbookUI) super.getUI();
    }

}
