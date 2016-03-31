package RegisterVaadin.backend;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.beanutils.BeanUtils;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import RegisterVaadin.backend.Registration;



public class RegistrationService {
	
	private HashMap<Long, Registration> registers = new HashMap<>();
    private long nextId = 0;

    public synchronized List<Registration> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        for (Registration register : registers.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || register.toString().toLowerCase()
                                .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(register.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(RegistrationService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Registration>() {

            @Override
            public int compare(Registration o1, Registration o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }
    
    
    public synchronized boolean check(String stringFilter) {
    	boolean passed=true;
        for (Registration register : registers.values()) {
            try {
               
                if (register.getUsername().toString().toLowerCase()
                        .equals(stringFilter.toLowerCase()) == true) {
                	 passed=false;
                	 
                }
               
            } catch (Exception ex) {
                Logger.getLogger(RegistrationService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        
        
        if(passed == true){
        	Page.getCurrent().setLocation("/mainPage");
        	return true;
        } else {
        	Notification.show("Username exists!", Notification.Type.WARNING_MESSAGE);
        	return false;
        }
    }

    public synchronized long count() {
        return registers.size();
    }

    public synchronized void save(Registration entry) {
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = (Registration) BeanUtils.cloneBean(entry);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        registers.put(entry.getId(), entry);
    }
}
