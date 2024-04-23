package advpro.b2.rasukanauth.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "is_staff", nullable = false)
    private boolean isStaff;

    @Column(name = "token")
    private String token;

    public User() {}

    public User(String name, String email, String password, String phoneNumber, Long balance, boolean isStaff, String token) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.isStaff = isStaff;
        this.token = token;
    }

    public User(UUID id, String name, String email, String password, String phoneNumber, Long balance, boolean isStaff, String token) {
        this(name, email, password, phoneNumber, balance, isStaff, token);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Long getBalance() {
        return balance;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public String getToken() {
        return token;
    }
}
