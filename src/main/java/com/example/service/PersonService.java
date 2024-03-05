package com.example.service;

import com.example.constants.RoleConstants;
import com.example.model.Person;
import com.example.model.Role;
import com.example.repository.PersonRepository;
import com.example.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createNewPerson(Person person) {
        boolean isSaved = false;
        Role role = roleRepository.getByRoleName(RoleConstants.STUDENT_ROLE);
        person.setRole(role);
        person.setPwd(passwordEncoder.encode(person.getPwd()));
        person = personRepository.save(person);
        if(person.getPersonId() > 0){
            isSaved = true;
        }
        return isSaved;
    }
}
