package com.fitnessapp.demo.service;

import com.fitnessapp.demo.config.ModelConfig;
import com.fitnessapp.demo.dto.UserRequest;
import com.fitnessapp.demo.dto.UserResponse;
import com.fitnessapp.demo.entity.User;
import com.fitnessapp.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private final ModelMapper modelMapper;

    @Autowired
    private final UserRepository repository;

    @Transactional // not needed as only one save operation is there
    public UserResponse registerUser(@Valid UserRequest request) {
        if(repository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already exists!");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser = repository.save(user);

        UserResponse resp = modelMapper.map(savedUser, UserResponse.class);
        return resp;
    }

    public UserResponse getProfileId(String userId) {
        User user = repository.findById(userId).orElseThrow(() -> new IllegalArgumentException("No user exists with id: " + userId));
        UserResponse resp = modelMapper.map(user, UserResponse.class);
        return resp;
    }

    public Boolean validateUser(String userId) {
        return repository.existsById(userId);
    }
}
