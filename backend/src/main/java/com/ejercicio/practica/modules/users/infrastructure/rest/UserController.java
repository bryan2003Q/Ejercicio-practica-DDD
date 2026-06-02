package com.ejercicio.practica.modules.users.infrastructure.rest;

import com.ejercicio.practica.modules.users.application.UserDTO;
import com.ejercicio.practica.modules.users.application.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public UserDTO createUser(@RequestBody Map<String, String> body) {
        return userService.createUser(body.get("name"), body.get("email"));
    }
}
