package com.matskevich.FirstSecurityApp.controllers;

import com.matskevich.FirstSecurityApp.dto.PersonDTO;
import com.matskevich.FirstSecurityApp.models.Person;
import com.matskevich.FirstSecurityApp.security.JWTUtil;
import com.matskevich.FirstSecurityApp.services.RegistrationService;
import com.matskevich.FirstSecurityApp.util.PersonValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(PersonValidator personValidator, RegistrationService registrationService,
                          JWTUtil jwtUtil, ModelMapper modelMapper) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public Map<String, String> performRegistration(@RequestBody @Valid PersonDTO personDTO,
                                                   BindingResult bindingResult) {
        Person person = convertToPerson(personDTO);

        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors())
            return Map.of("message", "Error!");       // лучше создать свой HandlerException

        registrationService.register(person);

        String token = jwtUtil.generateToken(person.getUsername());
        return Map.of("jwt-token", token);
    }

    public Person convertToPerson(PersonDTO personDTO) {
        return this.modelMapper.map(personDTO, Person.class);
    }
}
