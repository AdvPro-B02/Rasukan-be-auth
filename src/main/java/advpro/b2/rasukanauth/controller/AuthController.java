package advpro.b2.rasukanauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
class AuthController {

    @PostMapping("/register")
    @ResponseBody
    public String register() {
        return "Hi, this API currently is not functional. Thanks for the interest";
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
