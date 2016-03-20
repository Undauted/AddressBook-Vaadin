package addresbookVaadin.backend;

import org.apache.commons.beanutils.BeanUtils;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ContactService {

    
    static String[] fnames = { "Piotr", "Antoni", "Jan", "Mateusz", "Oliwier",
            "Norbert", "Alex", "Rafal", "Daniel", "Henryk", "Remigiusz", "Eugeniusz",
            "Leszek", "Tymon", "Karol", "Brajan", "Jerzy", "Slawek",
            "Jacek" };
    static String[] lnames = { "Kowalski", "Nowak", "Markowski", "Wietrzycki",
            "Sienkiewicz", "Mickiewicz", "Slowacki", "Kacprowicz", "Tusk", "Kaczynski",
            "Krampa", "Kempa", "Banasiak", "Strzala", "Kostrzewski", "Kurkiewicz",
            "Bontal", "Strzalka", "Kasprowicz" };
    static String[] stnames ={"Akacjowa", "Piastowska", "Pomorska", "Komorowskiego",
    		"Jaggielonska", "Wita Stwosza", "Przesmyk", "Lipnowska", "Bursztynowa", "Mickiewicza",
    		"Slowackiego", "Kawki", "Grunwaldzka", "Potokowa", "Jana Pawla II", "Gospody", "Chlopska",
    		"Kopernika", "Subislawa"};
    static String alphabetNumber = "ABCDEFGHIJKLMNOP";
    
    private static ContactService instance;

    public static ContactService createDemoService() {
        if (instance == null) {

            final ContactService contactService = new ContactService();

            Random r = new Random(0);
           
            int code =0;
            String CODE="",CODE2="";

            Calendar cal = Calendar.getInstance();
            for (int i = 0; i < 100; i++) {
                Contact contact = new Contact();
                contact.setFirstName(fnames[r.nextInt(fnames.length)]);
                contact.setLastName(lnames[r.nextInt(fnames.length)]);
                contact.setStreet(stnames[r.nextInt(lnames.length)]);
                contact.setNumber(r.nextInt(200)+1+Character.toString(alphabetNumber.charAt(r.nextInt(alphabetNumber.length()))));
                
                //Wyliczanie kodu pocztowego
                code = r.nextInt(100)+1;
    			if (code<10){
    				CODE = "0"+code;
    	        } else{
    	        	CODE = Integer.toString(code);
    	        }
    			
    			code = r.nextInt(1000)+1;
        		if (code<100){
        			CODE2 = "0"+code;
    		    } else{
    		    	CODE2 = Integer.toString(code);
    		    } 
                contact.setCode(CODE+"-"+CODE2);
                contact.setEmail(contact.getFirstName().toLowerCase() + "@"
                        + contact.getLastName().toLowerCase() + ".com");
                contact.setPhone("721-111-" + (100 + r.nextInt(900)));
                cal.set(1930 + r.nextInt(70),
                        r.nextInt(11), r.nextInt(28));
                contact.setBirthDate(cal.getTime());
                contactService.save(contact);
            }
            instance = contactService;
        }

        return instance;
    }

    private HashMap<Long, Contact> contacts = new HashMap<>();
    private long nextId = 0;

    public synchronized List<Contact> findAll(String stringFilter) {
        ArrayList arrayList = new ArrayList();
        for (Contact contact : contacts.values()) {
            try {
                boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                        || contact.toString().toLowerCase()
                                .contains(stringFilter.toLowerCase());
                if (passesFilter) {
                    arrayList.add(contact.clone());
                }
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ContactService.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
        Collections.sort(arrayList, new Comparator<Contact>() {

            @Override
            public int compare(Contact o1, Contact o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    public synchronized long count() {
        return contacts.size();
    }

    public synchronized void delete(Contact value) {
        contacts.remove(value.getId());
    }

    public synchronized void save(Contact entry) {
        if (entry.getId() == null) {
            entry.setId(nextId++);
        }
        try {
            entry = (Contact) BeanUtils.cloneBean(entry);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        contacts.put(entry.getId(), entry);
    }

}
