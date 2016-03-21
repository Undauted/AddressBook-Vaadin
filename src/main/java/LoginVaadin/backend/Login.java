package LoginVaadin.backend;

import java.io.Serializable;

import org.apache.commons.beanutils.BeanUtils;

public class Login implements Serializable, Cloneable{

		private Long id;
	    private String username = "";
	    private String password = "";
	
	    
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
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		
		 @Override
		    public Login clone() throws CloneNotSupportedException {
		        try {
		            return (Login) BeanUtils.cloneBean(this);
		        } catch (Exception ex) {
		            throw new CloneNotSupportedException();
		        }
		  }
		 
		 @Override
		    public String toString() {
		        return "Login{" + "id=" + id + ", username=" + username
		                + ", password=" + password + '}';
		    }

}
