package com.pluralsight.service;

import com.pluralsight.model.User;
import com.pluralsight.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    public User createUser(User user) {
        // Hash the password before saving
        String hashedPassword = BCrypt.hashpw(user.getHashed_Password(), BCrypt.gensalt());
        user.setHashed_Password(hashedPassword);
        return userRepository.save(user);
    }
    
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }
    
    public Optional<User> getUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User updateUser(Long userId, User updatedUser) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setUserName(updatedUser.getUserName());
                    user.setEmail(updatedUser.getEmail());
                    if (updatedUser.getHashed_Password() != null && !updatedUser.getHashed_Password().isEmpty()) {
                        user.setHashed_Password(BCrypt.hashpw(updatedUser.getHashed_Password(), BCrypt.gensalt()));
                    }
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }
    
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
    
    public boolean validatePassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
    
    public Optional<User> loginUser(String email, String password) {
        Optional<User> user = getUserByEmail(email);
        
        if (user.isPresent()) {
            // Check if password matches
            if (validatePassword(password, user.get().getHashed_Password())) {
                return user;
            }
        }
        
        return Optional.empty(); // Email not found or password doesn't match
    }
}
