package com.ces.assignment.note_taking.repository;

import com.ces.assignment.note_taking.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public User findByEmail(String email);
}
