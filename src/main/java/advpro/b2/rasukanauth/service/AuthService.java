package advpro.b2.rasukanauth.service;

import advpro.b2.rasukanauth.model.User;
import advpro.b2.rasukanauth.model.builder.UserBuilder;

import java.sql.SQLException;

public interface AuthService {
    String register(UserBuilder userBuilder);
    User login(String email, String password);
    String generateToken(String id);
    String tokenToId(String token);
    boolean validateToken(String token);
}
