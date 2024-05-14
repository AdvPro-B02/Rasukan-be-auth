package advpro.b2.rasukanauth.service;

import advpro.b2.rasukanauth.model.builder.UserBuilder;

import java.sql.SQLException;

public interface AuthService {
    String register(UserBuilder userBuilder);
    String login(String email, String password);
    String logout();
    String generateToken(String id);
    boolean validateToken();
}
