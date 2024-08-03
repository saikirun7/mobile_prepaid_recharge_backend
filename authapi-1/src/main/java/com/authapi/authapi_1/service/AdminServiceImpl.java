package com.authapi.authapi_1.service;

import com.authapi.authapi_1.entity.Admin;
import com.authapi.authapi_1.exception.UserAlreadyExistsException;
import com.authapi.authapi_1.exception.UserNotFoundException;
import com.authapi.authapi_1.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Admin addAdmin(Admin admin) throws UserAlreadyExistsException {
        Optional<Admin> existingUser = adminRepository.findByMobileAndEmail(admin.getMobile(), admin.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("Admin already exists with this mobile number or email");
        }
        return adminRepository.save(admin);
    }

    @Override
    public Admin validateUserByEmail(String email) throws UserNotFoundException {
        return adminRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
