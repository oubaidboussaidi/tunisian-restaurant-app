package com.oubaid.Restaurants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }
    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody LoginRequest loginRequest) {
        boolean isAuthenticated = userService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword());
        if (isAuthenticated) {
            Optional<User> user = userService.getUserByEmail(loginRequest.getEmail());
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    @GetMapping("/{email}/favorites")
    public ResponseEntity<List<Restaurant>> getFavoriteRestaurants(@PathVariable String email) {
        try {
            List<Restaurant> favoriteRestaurants = userService.getFavoriteRestaurantsByEmail(email);
            return ResponseEntity.ok(favoriteRestaurants);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/favorites")
    public ResponseEntity<Void> addFavoriteRestaurant(
            @RequestParam String email, @RequestParam String restaurantId) {
        try {
            userService.addFavoriteRestaurantByEmail(email, restaurantId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/favorites")
    public ResponseEntity<Void> removeFavoriteRestaurant(
            @RequestParam String email, @RequestParam String restaurantId) {
        try {
            userService.removeFavoriteRestaurantByEmail(email, restaurantId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{email}/addreviewsbyuser")
    public ResponseEntity<Void> addReviewsByUser(
            @PathVariable String email,
            @RequestParam String restaurantId,
            @RequestParam String reviewId) {
        try {
            userService.addReviewsByUser(email, restaurantId, reviewId);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }




}
