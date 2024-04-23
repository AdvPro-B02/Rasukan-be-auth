package advpro.b2.rasukanauth.model;

import advpro.b2.rasukanauth.model.builder.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private UserBuilder userBuilder;

    @BeforeEach
    void setUp() {
        userBuilder = new UserBuilder();
    }

    @Test
    void testCreateNormalUser_success() {
        UUID id = UUID.randomUUID();
        String name = "User 1";
        String email = "user1@test.com";
        String password = "user1";
        String phoneNumber = "1234567890";
        Long balance = 123456789L;
        userBuilder.setId(id);
        userBuilder.setName(name);
        userBuilder.setEmail(email);
        userBuilder.setPassword(password);
        userBuilder.setPhoneNumber(phoneNumber);
        userBuilder.setBalance(balance);
        User user = userBuilder.build();

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(phoneNumber, user.getPhoneNumber());
        assertEquals(balance, user.getBalance());
        assertFalse(user.isStaff());
        assertNull(user.getToken());
    }

    @Test
    void testCreateStaff_success() {
        UUID id = UUID.randomUUID();
        String name = "Staff 1";
        String email = "staff1@test.com";
        String password = "staff1";
        String phoneNumber = "0123456789";
        userBuilder.setId(id);
        userBuilder.setName(name);
        userBuilder.setEmail(email);
        userBuilder.setPassword(password);
        userBuilder.setPhoneNumber(phoneNumber);
        userBuilder.setIsStaff(true);
        User staff = userBuilder.build();

        assertEquals(id, staff.getId());
        assertEquals(name, staff.getName());
        assertEquals(email, staff.getEmail());
        assertEquals(password, staff.getPassword());
        assertEquals(phoneNumber, staff.getPhoneNumber());
        assertNull(staff.getBalance());
        assertTrue(staff.isStaff());
        assertNull(staff.getToken());
    }
}
