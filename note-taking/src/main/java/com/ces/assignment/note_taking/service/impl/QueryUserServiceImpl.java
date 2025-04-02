package com.ces.assignment.note_taking.service.impl;

import com.ces.assignment.note_taking.entity.User;
import com.ces.assignment.note_taking.repository.UserRepository;
import com.ces.assignment.note_taking.service.api.QueryUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QueryUserServiceImpl implements QueryUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
