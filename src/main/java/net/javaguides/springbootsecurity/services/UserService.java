package net.javaguides.springbootsecurity.services;

import net.javaguides.springbootsecurity.dto.UserRegistrationDto;
import net.javaguides.springbootsecurity.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User save(UserRegistrationDto registration);
}