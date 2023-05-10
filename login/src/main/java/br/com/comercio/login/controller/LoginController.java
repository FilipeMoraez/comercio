package br.com.comercio.login.controller;

import br.com.comercio.login.service.LoginService;
import br.com.comercio.login.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @PostMapping("login")
    public String login(@RequestBody UserDTO userDTO){
        return loginService.login(userDTO.getUsername(), userDTO.getPassword());
    }
}
