package ru.fabrichnaya.rentalcarsapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.fabrichnaya.rentalcarsapp.models.Person;
import ru.fabrichnaya.rentalcarsapp.services.impL.PeopleServiceImpl;

@Component
public class PersonValidator implements Validator {
    private final PeopleServiceImpl peopleService;

    @Autowired
    public PersonValidator(PeopleServiceImpl peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (peopleService.getPersonByEmail(person.getEmail()).isPresent()) {
            errors.rejectValue("email","", "This email is already taken");
        }


    }
}
