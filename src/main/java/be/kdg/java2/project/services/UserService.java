package be.kdg.java2.project.services;

import be.kdg.java2.project.domain.User;

public interface UserService {
    User findByEmail(String email);
    User findByUsername(String username);
    User findById(int id);

    void saveUser(User user);
}
