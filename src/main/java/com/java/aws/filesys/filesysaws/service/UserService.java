package com.java.aws.filesys.filesysaws.service;

import com.java.aws.filesys.filesysaws.exceptions.BadRequestException;
import com.java.aws.filesys.filesysaws.exceptions.NoSuchEntryException;
import com.java.aws.filesys.filesysaws.model.Role;
import com.java.aws.filesys.filesysaws.model.User;
import com.java.aws.filesys.filesysaws.model.enums.Authority;
import com.java.aws.filesys.filesysaws.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User save(User user) throws BadRequestException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(new Role(Authority.ROLE_USER)));
        return Optional.of(userRepository.save(user)).orElseThrow(() -> new BadRequestException(BadRequestException.DEFAULT_EX_MESSAGE));
    }

    @Transactional
    public User update(User user) {
        return entityManager.merge(user);
    }

    @Transactional
    public boolean delete(Integer id) {
        userRepository.deleteById(id);
        return true;
    }

    @Transactional
    public User findById(Integer id) throws NoSuchEntryException {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_EX_MESSAGE));
    }

    @Transactional
    public User findByUsername(String username) throws NoSuchEntryException {
        return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchEntryException(NoSuchEntryException.DEFAULT_EX_MESSAGE));
    }

    @Transactional
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
