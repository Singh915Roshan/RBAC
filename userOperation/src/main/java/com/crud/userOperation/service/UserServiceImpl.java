package com.crud.userOperation.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.crud.userOperation.dto.UserDto;
import com.crud.userOperation.entity.Role;
import com.crud.userOperation.entity.User;
import com.crud.userOperation.repository.RoleRepository;
import com.crud.userOperation.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.stream.Collectors;
import java.util.*;

public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDto userDto) {
    	 User user = new User();
         user.setName(userDto.getFirstName());
         String hashedPassword = hashPassword(userDto.getPassword());
         user.setPassword(hashedPassword);
         userRepository.save(user);
    }

    @Override
    public UserDto getUserById(Long id) {
    	   Optional<User> userOptional = userRepository.findById(id);
           if (userOptional.isEmpty()) {
               throw new EntityNotFoundException("User not found with ID: " + id);
           }
           User user = userOptional.get();
           UserDto userDto = new UserDto();
           userDto.setId(user.getId());
           userDto.setFirstName(user.getName());
           return userDto;
    }

    @Override
    public void updateUser(Long id, UserDto userDto) {
    	 Optional<User> userOptional = userRepository.findById(id);
         if (userOptional.isEmpty()) {
             throw new EntityNotFoundException("User not found with ID: " + id);
         }
         User user = userOptional.get();
         user.setName(userDto.getFirstName());
         userRepository.save(user);
    }

    @Override
    public void updatePartialUser(Long id, Map<String, Object> updates) {
    	 Optional<User> userOptional = userRepository.findById(id);
         if (userOptional.isEmpty()) {
             throw new EntityNotFoundException("User not found with ID: " + id);
         }

         User user = userOptional.get();

         // Apply updates to the user properties from the Map
         if (updates.containsKey("username")) {
             user.setName((String) updates.get("username"));
         }
         userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
     
    	Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }

        User user = userOptional.get();
        userRepository.delete(user);
    }
    private String hashPassword(String password) {
        
        return "hashed_" + password;
    }
    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));
        userRepository.save(user);
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
