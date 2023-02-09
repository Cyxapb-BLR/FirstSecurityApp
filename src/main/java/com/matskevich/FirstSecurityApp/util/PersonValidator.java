package com.matskevich.FirstSecurityApp.util;

import com.matskevich.FirstSecurityApp.models.Person;
import com.matskevich.FirstSecurityApp.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        Optional<Person> personFromDB = peopleService.loadUserByUsername(person.getUsername());

        if (personFromDB.isPresent()) {
            errors.rejectValue("username", "", "This username already exists");
        }
        //throw new UsernameNotFoundException("");

    }
}
