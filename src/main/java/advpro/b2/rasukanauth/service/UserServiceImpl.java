package advpro.b2.rasukanauth.service;

import advpro.b2.rasukanauth.model.User;

import advpro.b2.rasukanauth.model.builder.UserBuilder;
import advpro.b2.rasukanauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        Optional<User> user = userRepository.findById(UUID.fromString(id));
        if (user.isEmpty()) {
            throw new NoSuchElementException("User does not exists");
        }
        return user.get();
    }

    @Override
    public User createUser(UserBuilder userBuilder) {
        return userRepository.save(userBuilder.build());
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            throw new NoSuchElementException("Incorrect email or password");
        }
        return user;
    }

    @Override
    public int getUserBalance(String id) {
        User user = getUserById(id);
        return user.getBalance();
    }

    @Override
    public User updateUserBalance(String id, int saldo) {
        User user = getUserById(id);
        UserBuilder builder = new UserBuilder(user);
        builder.setBalance(saldo);
        return userRepository.save(builder.build());
    }
}
