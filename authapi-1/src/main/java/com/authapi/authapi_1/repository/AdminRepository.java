package com.authapi.authapi_1.repository;

import com.authapi.authapi_1.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByMobileAndEmail(String mobile, String email);
    Optional<Admin> findByEmail(String mobile);
}
