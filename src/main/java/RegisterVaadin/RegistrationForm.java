package RegisterVaadin;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import RegisterVaadin.backend.Registration;
import RegisterVaadin.backend.RegistrationService;
import addresbookVaadin.AddressbookUI;
import addresbookVaadin.ValidationMyAddress;
import addresbookVaadin.ValidationMyAddress.Letters;
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
	
	Button cancel = new Button("Cofnij", this::cancel);
    Button save = new Button("Zarejestruj", this::save);
   
    TextField username = new TextField("Uzytkownik");
    PasswordField password = new PasswordField("Haslo");
    PasswordField confirmPassword = new PasswordField("Potwierdz haslo");
    TextField email = new TextField("Email");

    RegistrationService serviceRegistration = new RegistrationService();
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
   
        ValidateMyRegister x = new ValidateMyRegister();
        
        HorizontalLayout actions = new HorizontalLayout(save,cancel);
        actions.setSpacing(true);
        
        username.addValidator(x.new Username());
        username.setImmediate(true);
        
        
        
        password.addValidator(x.new Password());
        password.setImmediate(true);

      
        
        confirmPassword.addValidator(new Check());
        confirmPassword.setImmediate(true);
        
        email.addValidator(x.new Email());
        email.setImmediate(true);
        email.setInputPrompt("eg. abc.a@wp.pl");
		addComponents(actions, username, password, confirmPassword, email);
    }

    
    public void save(Button.ClickEvent event) {
        try {
        	boolean passed = getUI().serviceRegistration.check(registration.getUsername());
        	if(passed){
	            formFieldBindings.commit();
	
	            
	            getUI().serviceRegistration.save(registration);
	
	            String msg = String.format("Zapisany '%s'.",
	            		registration.getUsername());
	            Notification.show(msg,Type.TRAY_NOTIFICATION);
	            getUI().refreshContacts();
        	}
            
        } catch (FieldGroup.CommitException e) {
           
        }
    }
    
    public void cancel(Button.ClickEvent event) {
        
        Notification.show("Cofnieto", Type.TRAY_NOTIFICATION);
        getUI().registrationList.select(null);
        getUI().refreshContacts();
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
    
//Walidacja potwierdzajaca haslo   
    class Check implements Validator {
        @Override
        public void validate(Object value)
                throws InvalidValueException {
            if (!(value instanceof String &&
                    ((String)value).equals(registration.getPassword()))) 
                throw new InvalidValueException("Hasla musza byc takie same");
        }

    }
    
 
}





