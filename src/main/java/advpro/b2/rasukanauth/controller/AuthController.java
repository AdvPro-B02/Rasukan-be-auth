package advpro.b2.rasukanauth.controller;

import advpro.b2.rasukanauth.model.User;
import advpro.b2.rasukanauth.model.builder.UserBuilder;
import advpro.b2.rasukanauth.service.AuthService;
import advpro.b2.rasukanauth.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@ModelAttribute UserBuilder userBuilder) {
        Map<String, String> body = new HashMap<>();
        body.put("status", "success");
        try {
            authService.register(userBuilder);
        } catch (Exception e) {
            body.put("status", "email already used");
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam String email, @RequestParam String password, HttpServletResponse response) {
        Map<String, String> body = new HashMap<>();
        body.put("status", "success");
        User user;
        try {
            user = authService.login(email, password);
        } catch (NoSuchElementException e) {
            body.put("status", "invalid email or password");
            return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
        }

        body.put("id", user.getId().toString());
        body.put("name", user.getName());
        body.put("balance", Integer.toString(user.getBalance()));
        String token = authService.generateToken(user.getId().toString());
        response.addCookie(new Cookie("token", token));
        response.addCookie(new Cookie("staff", Boolean.toString(user.isStaff())));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/validate")
    @ResponseBody
    public ResponseEntity<Map<String, Boolean>> validate(@CookieValue("token") String token) {
        Map<String, Boolean> body = new HashMap<>();
        body.put("valid", authService.validateToken(token));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
