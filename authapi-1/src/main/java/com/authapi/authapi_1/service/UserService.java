package com.authapi.authapi_1.service;

import com.authapi.authapi_1.entity.User;
import com.authapi.authapi_1.exception.UserAlreadyExistsException;
import com.authapi.authapi_1.exception.UserNotFoundException;

public interface UserService {
    User addUser(User user) throws UserAlreadyExistsException;
    User getUserByMobile(String mobile) throws UserNotFoundException;
}
