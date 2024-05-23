package advpro.b2.rasukanauth.service;

import advpro.b2.rasukanauth.model.User;
import advpro.b2.rasukanauth.model.builder.UserBuilder;
import advpro.b2.rasukanauth.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    private User testUser;
    private UserBuilder userBuilder;

    @BeforeEach
    void setUp() {
        userBuilder = new UserBuilder();
        userBuilder.setId(UUID.fromString("68e1011c-0021-4ae9-9d11-0deabf9cb449"));
        userBuilder.setName("TestService");
        userBuilder.setEmail("service@test.com");
        userBuilder.setPassword("servicetest");
        testUser = userBuilder.build();
    }

    @Test
    void testCreateUser_success() {
        doReturn(testUser).when(userRepository).save(any(User.class));

        User user = userService.createUser(userBuilder);
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(testUser.getId(), user.getId());
        assertEquals(testUser.getName(), user.getName());
        assertEquals(testUser.getEmail(), user.getEmail());
        assertEquals(testUser.getPassword(), user.getPassword());
        assertFalse(user.isStaff());
    }

    @Test
    void testGetUserById_success() {
        Optional<User> userOpt = Optional.of(testUser);
        doReturn(userOpt).when(userRepository).findById(any(UUID.class));

        User user = userService.getUserById(testUser.getId().toString());
        assertEquals(testUser.getId(), user.getId());
        assertEquals(testUser.getName(), user.getName());
        assertEquals(testUser.getEmail(), user.getEmail());
        assertEquals(testUser.getPassword(), user.getPassword());
        assertEquals(testUser.isStaff(), user.isStaff());
    }

    @Test
    void testGetUserById_fail() {
        doReturn(Optional.empty()).when(userRepository).findById(any(UUID.class));

        User user = userService.getUserById(testUser.getId().toString());
        assertNull(user);
    }

    @Test
    void testGetAllUsers() {
        List<User> userList = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            userBuilder.setId(UUID.randomUUID());
            userBuilder.setEmail(String.format("service%d@test.com", i));
            userList.add(userBuilder.build());
        }

        doReturn(userList).when(userRepository).findAll();
        List<User> allUser = userService.getAllUser();
        assertEquals(userList.size(), allUser.size());
        for (int i = 0; i < userList.size(); i++) {
            assertEquals(userList.get(i).getId(), allUser.get(i).getId());
            assertEquals(userList.get(i).getEmail(), allUser.get(i).getEmail());
        }
    }

    @Test
    void testGetUserByEmailAndPassword_success() {
        doReturn(testUser).when(userRepository).findByEmailAndPassword(any(String.class), any(String.class));

        String userId = userService.getUserByEmailAndPassword("service@test.com", "servicetest");
        verify(userRepository, times(1)).findByEmailAndPassword(any(String.class), any(String.class));
        assertEquals(userId, testUser.getId().toString());
    }

    @Test
    void testGetUserByEmailAndPassword_fail() {
        doReturn(null).when(userRepository).findByEmailAndPassword(any(String.class), any(String.class));

        assertThrows(
                NoSuchElementException.class,
                () -> userService.getUserByEmailAndPassword("service@service.com", "servicetest")
        );
        verify(userRepository, times(1)).findByEmailAndPassword(any(String.class), any(String.class));
    }

    @Test
    void testGetUserBalance_userExists() {
        int balance = 100;
        userBuilder.setBalance(balance);
        User user = userBuilder.build();
        Optional<User> userOpt = Optional.of(user);

        doReturn(userOpt).when(userRepository).findById(any(UUID.class));
        int userBalance = userService.getUserBalance(user.getId().toString());
        assertEquals(balance, userBalance);
    }

    @Test
    void testGetUserBalance_userDoesNotExists() {
        doReturn(Optional.empty()).when(userRepository).findById(any(UUID.class));

        assertThrows(NoSuchElementException.class, () -> {
            userService.getUserBalance("68e1011c-0021-4ae9-9d11-0deabf9cb449");
        });
    }

    @Test
    void testUpdateUserBalance_userExists() {
        int newBalance = 100;
        doReturn(Optional.of(testUser)).when(userRepository).findById(any(UUID.class));
        userBuilder.setBalance(newBalance);
        User user = userBuilder.build();
        doReturn(user).when(userRepository).save(any(User.class));

        assertNotEquals(newBalance, testUser.getBalance());
        User updatedUser = userService.updateUserBalance(testUser.getId().toString(), newBalance);
        assertEquals(testUser.getId(), updatedUser.getId());
        assertEquals(newBalance, updatedUser.getBalance());
    }

    @Test
    void testUpdateUserBalance_userDoesNotExists() {
        doReturn(Optional.empty()).when(userRepository).findById(any(UUID.class));

        assertThrows(NoSuchElementException.class, () -> {
            userService.updateUserBalance("68e1011c-0021-4ae9-9d11-0deabf9cb449", 100);
        });
    }
}
