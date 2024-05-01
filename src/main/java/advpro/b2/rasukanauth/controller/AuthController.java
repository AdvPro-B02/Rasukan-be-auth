package advpro.b2.rasukanauth.controller;

import advpro.b2.rasukanauth.model.builder.UserBuilder;
import advpro.b2.rasukanauth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@ModelAttribute UserBuilder userBuilder) {
        try {
            authService.register(userBuilder);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        String token;
        try {
            token = authService.login(email, password);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @PostMapping("/logout")
    @ResponseBody
    public String logout() {
        return "Hi, this API currently is not functional. Thanks for the interest";
    }

    @PostMapping("/get-token")
    @ResponseBody
    public String getToken() {
        return "Hi, this API currently is not functional. Thanks for the interest";
    }
}
