package ru.itmentor.spring.boot_security.demo.service;


import ru.itmentor.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getUser(Long id);

    User getUserByUsername(String username);

    void addUser(User user);

    void update(User user);

    void remove(Long id);
}
