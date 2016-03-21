package LoginVaadin.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.beanutils.BeanUtils;

import LoginVaadin.backend.Login;


public class LoginService {
	
	private HashMap<Long, Login> logins = new HashMap<>();
    private long nextId = 0;

    public synchronized List<Login> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        for (Login register : logins.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || register.toString().toLowerCase()
                                .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(register.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(LoginService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Login>() {

            @Override
            public int compare(Login o1, Login o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    public synchronized long count() {
        return logins.size();
    }

    public synchronized void save(Login entry) {
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = (Login) BeanUtils.cloneBean(entry);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        logins.put(entry.getId(), entry);
    }

}
