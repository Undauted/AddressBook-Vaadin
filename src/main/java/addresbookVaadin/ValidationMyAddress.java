package addresbookVaadin;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.InvalidValueException;

public class ValidationMyAddress {

	public class Letters implements Validator {
        @Override
        public void validate(Object value)
                throws InvalidValueException {
            if (!(value instanceof String &&
                    ((String)value).matches("[A-Z]{1}[a-z]+")))
                throw new InvalidValueException("Tylko litery. Pierwsza duza, pozostale male");
        }

    }
    
    public class Phone implements Validator {
        @Override
        public void validate(Object value)
                throws InvalidValueException {
            if (!(value instanceof String &&
                    ((String)value).matches("[1-9]{3}[-]{1}[0-9]{3}[-]{1}[0-9]{3}")))
                throw new InvalidValueException("Niepoprawny numer telefonu");
        }

    }
    
    public class Email implements Validator {
        @Override
        public void validate(Object value)
                throws InvalidValueException {
            if (!(value instanceof String &&
                    ((String)value).matches("[A-Za-z0-9.-_]+[@]{1}[A-Za-z0-9-_]+[.]{1}[A-Za-z]{2,4}")))
                throw new InvalidValueException("Niepoprawny email");
        }

    }
    
    public class Street implements Validator {
        @Override
        public void validate(Object value)
                throws InvalidValueException {
            if (!(value instanceof String &&
                    ((String)value).matches("[A-Z]{1}[a-z]+[ ]?[A-Z]*[a-z]*[ ]?[A-Z]*[a-z]*")))
                throw new InvalidValueException("Niepoprawnie wpisana ulica.Tylko litery");
        }

    }
    
    public class Numbers implements Validator {
        @Override
        public void validate(Object value)
                throws InvalidValueException {
            if (!(value instanceof String &&
                    ((String)value).matches("[1-9]{1,5}[A-Z]?[/]?[0-9]*")))
                throw new InvalidValueException("Niepoprawny numer");
        }

    }
    
    public class Code implements Validator {
        @Override
        public void validate(Object value)
                throws InvalidValueException {
            if (!(value instanceof String &&
                    ((String)value).matches("[1-9]{1}[0-9]{1}[-]{1}[1-9]{1}[0-9]{2}")))
                throw new InvalidValueException("Niepoprawny kod");
        }

    }
    
}
