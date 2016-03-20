package addresbookVaadin;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import addresbookVaadin.backend.Contact;
import addresbookVaadin.ValidationMyAddress;
import addresbookVaadin.ValidationMyAddress.Code;
import addresbookVaadin.ValidationMyAddress.Email;
import addresbookVaadin.ValidationMyAddress.Letters;
import addresbookVaadin.ValidationMyAddress.Numbers;
import addresbookVaadin.ValidationMyAddress.Phone;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;



public class ContactForm extends FormLayout {

    Button save = new Button("Zapisz", this::save);
    Button delete = new Button("Usun", this::delete);
    Button cancel = new Button("Cofnij", this::cancel);
    TextField firstName = new TextField("Imie");
    TextField lastName = new TextField("Nazwisko");
    TextField street = new TextField("Ulica");
    TextField number = new TextField("Numer domu");
    TextField code = new TextField("Kod pocztowy");
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

        ValidationMyAddress x = new ValidationMyAddress();
        
        HorizontalLayout actions = new HorizontalLayout(save,delete, cancel);
        actions.setSpacing(true);

        firstName.addValidator(x.new Letters());
        firstName.setImmediate(true);
       
        lastName.addValidator(x.new Letters());
        lastName.setImmediate(true);
        
        phone.addValidator(x.new Phone());
        phone.setImmediate(true);
        
        email.addValidator(x.new Email());
        email.setImmediate(true);
       
        street.addValidator(x.new Street());
        street.setImmediate(true);
        
        number.addValidator(x.new Numbers());
        number.setImmediate(true);
        
        code.addValidator(x.new Code());
        code.setImmediate(true);
        
		addComponents(actions, firstName, lastName, street, number, code, phone, email, birthDate);
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
        getUI().refreshContacts();
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
    
    public void edit(Contact contact) {
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




