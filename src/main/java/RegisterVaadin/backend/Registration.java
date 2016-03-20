package RegisterVaadin.backend;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import addresbookVaadin.backend.Contact;

public class Registration implements Serializable, Cloneable{
	
	private Long id;
    private String username = "";
    private String password = "";
    private String confirmPassword = "";
    private String email = "";
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	 @Override
	    public Registration clone() throws CloneNotSupportedException {
	        try {
	            return (Registration) BeanUtils.cloneBean(this);
	        } catch (Exception ex) {
	            throw new CloneNotSupportedException();
	        }
	  }
	 
	 @Override
	    public String toString() {
	        return "Registration{" + "id=" + id + ", username=" + username
	                + ", password=" + password + ", confrimPassword=" + confirmPassword + ", email="
	                + email + '}';
	    }
 

}
