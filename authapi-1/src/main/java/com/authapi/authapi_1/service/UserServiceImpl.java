package com.authapi.authapi_1.service;

import com.authapi.authapi_1.entity.User;
import com.authapi.authapi_1.exception.UserAlreadyExistsException;
import com.authapi.authapi_1.exception.UserNotFoundException;
import com.authapi.authapi_1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser(User user) throws UserAlreadyExistsException {
        Optional<User> existingUser = userRepository.findByMobileAndEmail(user.getMobile(), user.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User already exists with this mobile number or email");
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserByMobile(String mobile) throws UserNotFoundException {
        return userRepository.findByMobile(mobile)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
