package com.mavericksstube.maverickshub.services;

import com.mavericksstube.maverickshub.dtos.requests.CreateUserRequest;
import com.mavericksstube.maverickshub.dtos.response.CreateUserResponse;
import com.mavericksstube.maverickshub.exceptions.UserNotFoundException;
import com.mavericksstube.maverickshub.models.Authority;
import com.mavericksstube.maverickshub.models.User;
import com.mavericksstube.maverickshub.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
//@AllArgsConstructor              this or use the constructor injection
public class MavericksHubUserService implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public MavericksHubUserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public CreateUserResponse register(CreateUserRequest createUserRequest) {
        User user = modelMapper.map(createUserRequest, User.class);
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.setAuthorities(new HashSet<>());
        user.getAuthorities().add(Authority.USER);
        User savedUser = userRepository.save(user);
        CreateUserResponse response = modelMapper.map(savedUser, CreateUserResponse.class);
        response.setMessage("user registered successfully");
        return response;
    }

    @Override
    public User getById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(()->
                new UserNotFoundException(String.format("User with id %d not found", id)));
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(()->new UserNotFoundException("User not found"));
    }
}
