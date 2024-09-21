package ru.itmentor.spring.boot_security.demo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.repository.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return repository.findByUserName(username);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        repository.save(user);
    }


    @Override
    @Transactional
    public void update(User user) {
        repository.save(user);
    }

    @Override
    @Transactional
    public void remove(Long id) {
        repository.deleteById(id);

    }
}
