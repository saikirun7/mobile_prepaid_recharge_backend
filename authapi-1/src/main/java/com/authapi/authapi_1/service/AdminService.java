package com.authapi.authapi_1.service;

import com.authapi.authapi_1.entity.Admin;
import com.authapi.authapi_1.exception.UserAlreadyExistsException;
import com.authapi.authapi_1.exception.UserNotFoundException;

public interface AdminService {
    Admin addAdmin(Admin admin) throws UserAlreadyExistsException;
    Admin validateUserByEmail(String email) throws UserNotFoundException;
}
