package com.mavericksstube.maverickshub.services;

import com.mavericksstube.maverickshub.dtos.requests.CreateUserRequest;
import com.mavericksstube.maverickshub.dtos.response.CreateUserResponse;
import com.mavericksstube.maverickshub.dtos.response.UserResponse;
import com.mavericksstube.maverickshub.exceptions.UserNotFoundException;
import com.mavericksstube.maverickshub.models.User;

public interface UserService {
    CreateUserResponse register(CreateUserRequest createUserRequest);

    User getById(Long id) throws UserNotFoundException;

    User getUserByUsername(String username) throws UserNotFoundException;
}
