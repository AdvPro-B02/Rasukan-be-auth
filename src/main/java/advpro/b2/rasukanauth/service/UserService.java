package advpro.b2.rasukanauth.service;

import advpro.b2.rasukanauth.model.User;
import advpro.b2.rasukanauth.model.builder.UserBuilder;

import java.util.List;

public interface UserService {
    List<User> getAllUser();
    User getUserById(String id);
    User createUser(UserBuilder userBuilder);
    User getUserByEmailAndPassword(String email, String password);
    int getUserBalance(String id);
    User updateUserBalance(String id, int balance);
}
