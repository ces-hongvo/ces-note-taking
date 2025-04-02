package com.ces.assignment.note_taking.service.impl;

import com.ces.assignment.note_taking.entity.User;
import com.ces.assignment.note_taking.repository.UserRepository;
import com.ces.assignment.note_taking.service.api.CommandUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandUserServiceImpl implements CommandUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User saveUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);

        return userRepository.save(user);
    }
}
