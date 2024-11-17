package com.example.finalcharity.main.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // General user functions
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        User createdUser = userService.addUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Optional<User> updatedUser = userService.updateUser(id, user);
        if (updatedUser.isPresent()) {
            return new ResponseEntity<>(updatedUser.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    
    // Admin-specific functions
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/suspended")
    public List<User> getSuspendedUsers() {
        return userService.getSuspendedUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/suspend/{id}")
    public ResponseEntity<?> suspendUser(@PathVariable Integer id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            user.get().setActive(false);
            userService.addUser(user.get());  // Save the updated user status
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/unsuspend/{id}")
    public ResponseEntity<?> unsuspendUser(@PathVariable Integer id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            user.get().setActive(true);
            userService.addUser(user.get());  // Save the updated user status
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
    
}