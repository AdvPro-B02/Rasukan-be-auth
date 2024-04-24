package advpro.b2.rasukanauth.service;

import advpro.b2.rasukanauth.model.builder.UserBuilder;

public interface AuthService {
    String register(UserBuilder userBuilder);
    String login();
    String logout();
    String generateToken();
    boolean validateToken();
}
