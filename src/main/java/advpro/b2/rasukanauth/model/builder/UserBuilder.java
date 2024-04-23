package advpro.b2.rasukanauth.model.builder;

import advpro.b2.rasukanauth.model.User;

import java.util.UUID;

public class UserBuilder {
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private Long balance;
    private boolean isStaff;
    private String token;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public void setIsStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User build() {
        return new User(id, name, email, password, phoneNumber, balance, isStaff, token);
    }
}
