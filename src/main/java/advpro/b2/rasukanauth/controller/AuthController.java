package advpro.b2.rasukanauth.controller;

import advpro.b2.rasukanauth.model.builder.UserBuilder;
import advpro.b2.rasukanauth.service.AuthService;
import advpro.b2.rasukanauth.service.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
class AuthController {

//    AuthService authService = new AuthServiceImpl();

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    @ResponseBody
    public String register(@ModelAttribute UserBuilder userBuilder) {
        return authService.register(userBuilder);
    }

    @PostMapping("/login")
    @ResponseBody
    public String login() {
        return "Hi, this API currently is not functional. Thanks for the interest";
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
