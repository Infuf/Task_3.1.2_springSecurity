package ru.itmentor.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.dto.UserDTO;
import ru.itmentor.spring.boot_security.demo.entity.User;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/admin/v0.1")
@RequiredArgsConstructor
public class AdminRestController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = userService.getAll();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users.
                stream()
                .map(UserDTO::new)
                .toList());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        if (user != null) {
            UserDTO dto = new UserDTO(user);
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getId() != null) {
            ResponseEntity responseEntity = new ResponseEntity<>("Id must be null to create", HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setAge(userDTO.getAge());

        userService.addUser(user);
        userDTO.setId(user.getId());

        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping("/user")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getId() == null) {
            ResponseEntity responseEntity = new ResponseEntity<>("Id must not be null to update", HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
        User oldUser = userService.getUser(userDTO.getId());
        if (oldUser == null) {
            ResponseEntity responseEntity = new ResponseEntity<>("User not exists", HttpStatus.BAD_REQUEST);
            return responseEntity;
        }

        if (userDTO.getUserName() != null) {
            oldUser.setUserName(userDTO.getUserName());
        }
        if (userDTO.getFirstName() != null) {
            oldUser.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null) {
            oldUser.setLastName(userDTO.getLastName());
        }
        if (userDTO.getEmail() != null) {
            oldUser.setEmail(userDTO.getEmail());
        }
        if (userDTO.getAge() != null) {
            oldUser.setAge(userDTO.getAge());
        }

        userService.update(oldUser);

        return ResponseEntity.ok(userDTO);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        userService.remove(id);
        return ResponseEntity.ok(id);
    }
}
