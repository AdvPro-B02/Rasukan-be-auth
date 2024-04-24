package advpro.b2.rasukanauth.service;

import advpro.b2.rasukanauth.model.User;
import advpro.b2.rasukanauth.model.builder.UserBuilder;

import java.util.List;

public interface UserService {
    List<User> getAllUser();
    User getUserById();
    User createUser(UserBuilder userBuilder);
}
