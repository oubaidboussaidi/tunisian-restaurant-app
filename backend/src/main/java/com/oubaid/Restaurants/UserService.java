package com.oubaid.Restaurants;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RestaurantService restaurantService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, RestaurantService restaurantService) {
        this.userRepository = userRepository;
        this.restaurantService = restaurantService;
    }

    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already in use!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public boolean authenticateUser(String email, String rawPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.isPresent() && passwordEncoder.matches(rawPassword, userOptional.get().getPassword());
    }
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Restaurant> getFavoriteRestaurantsByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            List<String> favoriteImdbIds = user.get().getFavoriteRestaurants();
            return favoriteImdbIds.stream()
                    .map(imdbId -> restaurantService.singleRestaurant(imdbId)
                            .orElseThrow(() -> new RuntimeException("Restaurant not found: " + imdbId)))
                    .toList();
        } else {
            throw new RuntimeException("User not found");
        }
    }


    public void addFavoriteRestaurantByEmail(String email, String restaurantId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> favoriteRestaurantIds = user.getFavoriteRestaurants();
        if (!favoriteRestaurantIds.contains(restaurantId)) {
            favoriteRestaurantIds.add(restaurantId);
            userRepository.save(user);
        }
    }

    public void removeFavoriteRestaurantByEmail(String email, String restaurantId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> favoriteRestaurantIds = user.getFavoriteRestaurants();
        if (favoriteRestaurantIds.contains(restaurantId)) {
            favoriteRestaurantIds.remove(restaurantId);
            userRepository.save(user);
        }
    }
    public void addReviewsByUser(String email, String restaurantId, String reviewId) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getRestaurantReviews().put(restaurantId, reviewId);
            userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }



}


