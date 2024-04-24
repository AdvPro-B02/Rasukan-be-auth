package advpro.b2.rasukanauth.service;

import advpro.b2.rasukanauth.model.User;
import advpro.b2.rasukanauth.model.builder.UserBuilder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class AuthServiceImpl implements AuthService {

    private UserService userService;

    @Autowired
    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public AuthServiceImpl() {}

    @Override
    public String register(UserBuilder userBuilder) {
        User user = userService.createUser(userBuilder);
        return user.getId().toString();
    }

    @Override
    public String login() {
        return null;
    }

    @Override
    public String logout() {
        return null;
    }

    @Override
    public String generateToken() {
        return null;
    }

    @Override
    public boolean validateToken() {
        return false;
    }
}
