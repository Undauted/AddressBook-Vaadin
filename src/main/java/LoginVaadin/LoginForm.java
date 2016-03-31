package LoginVaadin;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;



import LoginVaadin.backend.Login;
import RegisterVaadin.ValidateMyRegister.Password;
import RegisterVaadin.ValidateMyRegister.Username;

public class LoginForm extends FormLayout {
	
	Button cancel = new Button("Cofnij", this::cancel);
    Button save = new Button("Zaloguj", this::save);
   
    TextField username = new TextField("Uzytkownik");
    PasswordField password = new PasswordField("Haslo");

    BeanFieldGroup<Login> formFieldBindings;
	private Login login;

    public LoginForm() {
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
   
       ValidateMyLogin x = new ValidateMyLogin();
        
        HorizontalLayout actions = new HorizontalLayout(save,cancel);
        actions.setSpacing(true);
        
        username.addValidator(x.new Username());
        username.setImmediate(true);
        
        password.addValidator(x.new Password());
        password.setImmediate(true);
        
        
		addComponents(actions, username, password);
    }

    
    public void save(Button.ClickEvent event) {
        try {
          
            formFieldBindings.commit();

            
           
            Page.getCurrent().setLocation("/mainPage");
            
        } catch (FieldGroup.CommitException e) {
           
        }
    }
    
    public void cancel(Button.ClickEvent event) {
        
        Notification.show("Cofnieto", Type.TRAY_NOTIFICATION);
    
    }

    public void edit(Login login) {
        this.login = login;
        if(login != null) {
           
            formFieldBindings = BeanFieldGroup.bindFieldsBuffered(login, this);
            username.focus();
        }
        setVisible(login != null);
        
    }

    @Override
    public LoginUI getUI() {
        return (LoginUI) super.getUI();
    }
    

}
