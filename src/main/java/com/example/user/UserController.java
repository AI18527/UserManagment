package com.example.user;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private InterestService interestService;

    @Autowired
    HttpSession session;

    @PostMapping("/register")
    public User createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserRequest userRequest) {
        try {
            String id = userService.Login(userRequest);
            if (id != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Set-Cookie", id);
                session.setAttribute("token", id);
                return ResponseEntity.status(HttpStatus.OK).headers(headers).body("Welcome " + id);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid credentials");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), e.getMessage());
        }
    }


    @GetMapping("/{id}/interests")
    public List<Interest> getUserInterests(@PathVariable String id){
        return userService.getUserById(id).getInterests();
    }

    @PostMapping("/{userId}/interest/{interestId}")
    public User addUserInterest(@PathVariable String userId, @PathVariable int interestId){
        User user = userService.getUserById(userId);
        Interest interest = interestService.getInterestById(interestId);

        user.getInterests().add(interest);
        userService.deleteUser(userId);
        //userService.updateUser(new UserRequest(user.getUsername()));

        return user;
    }

    @DeleteMapping("/{userId}/interest/{interestId}")
    public User deleteUserInterest(@PathVariable String userId, @PathVariable int interestId) {
        User user = userService.getUserById(userId);
        Interest interest = interestService.getInterestById(interestId);

        user.getInterests().remove(interest);
        //userService.saveUser(user);

        return user;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
