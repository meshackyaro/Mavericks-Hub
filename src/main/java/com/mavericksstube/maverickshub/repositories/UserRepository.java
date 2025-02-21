package com.mavericksstube.maverickshub.repositories;

import com.mavericksstube.maverickshub.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("select u from User where u.email=:email")
    Optional<User> findByEmail(String email);
}
