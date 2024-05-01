package advpro.b2.rasukanauth.service;

import advpro.b2.rasukanauth.model.User;
import advpro.b2.rasukanauth.model.builder.UserBuilder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Base64;
import java.util.NoSuchElementException;

@Service
public class AuthServiceImpl implements AuthService {

    private final byte[] key = {77, 45, 90, 13, 121, 59};

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
    public String login(String email, String password) throws NoSuchElementException {
        String id = userService.getUserByEmailAndPassword(email, password);
        return generateToken(id);
    }

    @Override
    public String logout() {
        return null;
    }

    @Override
    public String generateToken(String id) {
        byte[] idBytes = id.getBytes();
        for (int i = 0; i < idBytes.length; i++) {
            idBytes[i] ^= key[i % key.length];
        }
        return Base64.getEncoder().encodeToString(idBytes);
    }

    @Override
    public boolean validateToken() {
        return false;
    }
}
