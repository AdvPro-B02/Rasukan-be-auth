package advpro.b2.rasukanauth.service;

import advpro.b2.rasukanauth.model.User;

import advpro.b2.rasukanauth.model.builder.UserBuilder;
import advpro.b2.rasukanauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUser() {
        return null;
    }

    @Override
    public User getUserById() {
        return null;
    }

    @Override
    public User createUser(UserBuilder userBuilder) {
        return userRepository.save(userBuilder.build());
    }

    @Override
    public String getUserByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            throw new NoSuchElementException("Incorrect email or password");
        }
        return user.getId().toString();
    }
}
