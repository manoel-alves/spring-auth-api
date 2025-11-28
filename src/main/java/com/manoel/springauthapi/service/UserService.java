package com.manoel.springauthapi.service;

import com.manoel.springauthapi.dto.UserChangePasswordRequest;
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
            throw new EmailAlreadyRegisteredException();
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
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(rawPassword, user.getPassword())){
            throw new InvalidPasswordException();
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
                .orElseThrow(UserNotFoundException::new);

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        return userRepository.save(user);
    }

    public void changePassword(Long id, UserChangePasswordRequest request){
        User user = userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);

        if  (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new InvalidPasswordException();
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }

        userRepository.deleteById(id);
    }
}