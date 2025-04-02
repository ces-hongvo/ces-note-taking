package com.ces.assignment.note_taking.service.api;

import com.ces.assignment.note_taking.entity.User;

public interface CommandUserService {
    User saveUser(String name, String email);
}
