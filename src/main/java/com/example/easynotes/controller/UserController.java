package com.example.easynotes.controller;

import com.example.easynotes.model.User;
import com.example.easynotes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/data")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/user")
    public List<User> getAllUsers(@RequestParam(value = "user_name", required = false) String userName){
        if (userName == null){
            return userRepository.findAll();
        }else{
            return userRepository.findByUserName(userName);
        }
    }

    @PostMapping("/user")
    public User createUser(@RequestBody @Valid User user){
        return userRepository.save(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> createUser(@PathVariable(value = "id") Long userId){
        User user = userRepository.findOne(userId);
        if( user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId,
                                           @Valid @RequestBody User userDetails){
        User user = userRepository.findOne(userId);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setUserName(userDetails.getUserName());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());

        User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long userId){
        User user = userRepository.findOne(userId);
        if(user == null){
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }
}
