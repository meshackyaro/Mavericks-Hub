package com.mavericksstube.maverickshub.services;

import com.mavericksstube.maverickshub.dtos.requests.CreateUserRequest;
import com.mavericksstube.maverickshub.dtos.response.CreateUserResponse;
import com.mavericksstube.maverickshub.exceptions.UserNotFoundException;
import com.mavericksstube.maverickshub.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    public UserService userService;

    @Test
    public void registerTest(){
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@email.com");
        createUserRequest.setPassword("password");

        CreateUserResponse response = userService.register(createUserRequest);

        assertNotNull(response);
        assertTrue(response.getMessage().contains("success"));
    }

    @Test
    @DisplayName("test that user can be retrieved by id")
    @Sql(scripts = {"/db/data.sql"})
    public void testGetUserById() throws UserNotFoundException {
        User user = userService.getById(200L);
        assertThat(user).isNotNull();
    }
}
