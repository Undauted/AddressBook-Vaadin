package LoginVaadin;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;





@Title("Login")
@Theme("valo")
public class LoginUI extends UI {

    @Override
    protected void init(VaadinRequest request) {

        
        new Navigator(this, this);

     
        getNavigator().addView(LoginView.NAME, LoginView.class);//


        getNavigator().addView(LoginMainView.NAME,
                LoginMainView.class);

       
        getNavigator().addViewChangeListener(new ViewChangeListener() {

            public boolean beforeViewChange(ViewChangeEvent event) {

                // Check if a user has logged in
                boolean isLoggedIn = getSession().getAttribute("user") != null;
                boolean isLoginView = event.getNewView() instanceof LoginView;

                if (!isLoggedIn && !isLoginView) {
                    // Redirect to login view always if a user has not yet
                    // logged in
                    getNavigator().navigateTo(LoginView.NAME);
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    
                    return false;
                }

                return true;
            }

            public void afterViewChange(ViewChangeEvent event) {

            }
        });
    }

	@WebServlet(urlPatterns = {"/login/*"}, name = "MyLogin", asyncSupported = true)
    @VaadinServletConfiguration(ui = LoginUI.class, productionMode = false)
    public static class MyLogin extends VaadinServlet {
    }
	
}
