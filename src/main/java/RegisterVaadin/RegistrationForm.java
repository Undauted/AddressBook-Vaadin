package RegisterVaadin;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import RegisterVaadin.backend.Registration;
import addresbookVaadin.AddressbookUI;
import addresbookVaadin.backend.Contact;

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



public class RegistrationForm extends FormLayout {

    Button save = new Button("Zapisz", this::save);
    TextField username = new TextField("Uzytkownik");
    TextField password = new TextField("Haslo");
    TextField confirmPassword = new TextField("Potwierdz haslo");
    TextField email = new TextField("Email");


  
    
    
    
    
    
    BeanFieldGroup<Registration> formFieldBindings;
	private Registration registration;

    public RegistrationForm() {
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
   
        HorizontalLayout actions = new HorizontalLayout(save);
        actions.setSpacing(true);
        
		addComponents(actions, username, password, confirmPassword, email);
    }

    
    public void save(Button.ClickEvent event) {
        try {
          
            formFieldBindings.commit();

            
            getUI().service.save(registration);

            String msg = String.format("Zapisany '%s %s'.",
            		registration.getUsername(),
            		registration.getPassword());
            Notification.show(msg,Type.TRAY_NOTIFICATION);
            getUI().refreshContacts();
            
        } catch (FieldGroup.CommitException e) {
           
        }
    }

    public void edit(Registration registration) {
        this.registration = registration;
        if(registration != null) {
           
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(registration, this);
            username.focus();
        }
        setVisible(registration != null);
        
    }

    @Override
    public RegistrationUI getUI() {
        return (RegistrationUI) super.getUI();
    }
}





