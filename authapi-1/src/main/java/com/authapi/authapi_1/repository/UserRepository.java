package com.authapi.authapi_1.repository;

import com.authapi.authapi_1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByMobileAndEmail(String mobile, String email);
    Optional<User> findByMobile(String mobile);
}
