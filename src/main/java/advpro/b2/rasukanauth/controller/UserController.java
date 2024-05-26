package advpro.b2.rasukanauth.controller;

import advpro.b2.rasukanauth.model.User;
import advpro.b2.rasukanauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/users")
class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getUser(@PathVariable("id") String id) {
        User user;
        try {
            user = userService.getUserById(id);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return new ResponseEntity<>(bodyFail("User does not exists"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bodySuccess(user), HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Map<String, String>>> getAllUsers() {
        List<User> allUsers = userService.getAllUser();
        List<Map<String, String>> body = new ArrayList<>();
        for (User user : allUsers) {
            Map<String, String> map = new HashMap<>();
            map.put("id", user.getId().toString());
            map.put("name", user.getName());
            map.put("balance", Integer.toString(user.getBalance()));
            body.add(map);
        }
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @GetMapping("/{id}/balance")
    @ResponseBody
    public ResponseEntity<Map<String, String>> getUserBalance(@PathVariable("id") String id) {
        int balance;
        try {
            balance = userService.getUserBalance(id);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return new ResponseEntity<>(bodyFail("User does not exists"), HttpStatus.BAD_REQUEST);
        }

        Map<String, String> body = new HashMap<>();
        body.put("success", "true");
        body.put("balance", Integer.toString(balance));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PutMapping("/{id}/balance")
    @ResponseBody
    public ResponseEntity<Map<String, String>> updateUserBalance(@PathVariable("id") String id, @RequestParam int balance) {
        User user;
        try {
            user = userService.updateUserBalance(id, balance);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            return new ResponseEntity<>(bodyFail("User does not exists"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(bodySuccess(user), HttpStatus.OK);
    }

    private Map<String, String> bodySuccess(User user) {
        Map<String, String> body = new HashMap<>();
        body.put("success", "true");
        body.put("id", user.getId().toString());
        body.put("name", user.getName());
        body.put("balance", Integer.toString(user.getBalance()));
        return body;
    }

    private Map<String, String> bodyFail(String message) {
        Map<String, String> body = new HashMap<>();
        body.put("success", "false");
        body.put("message", message);
        return body;
    }
}
