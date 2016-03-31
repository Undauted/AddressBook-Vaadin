package LoginVaadin;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;

public class ValidateMyLogin {

	public class Username implements Validator {
        @Override
        public void validate(Object value)
                throws InvalidValueException {
            if (!(value instanceof String &&
                    ((String)value).matches("[A-Za-z]{1}[A-Za-z0-9]+")))
                throw new InvalidValueException("Niepoprawna nazwa uzytkownika. Tylko litery i liczby. Uzytkownik nie moze zaczynac sie o liczby");
        }

    }
    
    public class Password implements Validator {
        @Override
        public void validate(Object value)
                throws InvalidValueException {
            if (!(value instanceof String &&
                    ((String)value).matches("[A-Za-z0-9./'!]+")))
                throw new InvalidValueException("Niepoprawny format hasla");
        }
    }
}