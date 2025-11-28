package com.manoel.springauthapi.service;

import com.manoel.springauthapi.dto.UserRegisterRequest;
import com.manoel.springauthapi.dto.UserUpdateRequest;
import com.manoel.springauthapi.entity.User;
import com.manoel.springauthapi.exception.EmailAlreadyRegisteredException;
import com.manoel.springauthapi.exception.InvalidPasswordException;
import com.manoel.springauthapi.exception.UserNotFoundException;
import com.manoel.springauthapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyRegisteredException("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        return userRepository.save(user);
    }

    public User login (String email, String rawPassword){
        User user = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())){
            throw new InvalidPasswordException("Invalid password");
        }

        return user;
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User updateUser(Long id, UserUpdateRequest request){
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        return userRepository.save(user);
    }

    public void changePassword(Long id, String oldPassword, String newPassword){
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if  (!passwordEncoder.matches(oldPassword, user.getPassword())){
            throw new InvalidPasswordException("Invalid password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }



    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }

        userRepository.deleteById(id);
    }
}