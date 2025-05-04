package com.oubaid.Restaurants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/restaurants")
@CrossOrigin(origins = "*")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants(){
        return new ResponseEntity<List<Restaurant>>(restaurantService.allRestaurants(), HttpStatus.OK);
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<Optional<Restaurant>> getSingleRestaurant(@PathVariable String imdbId){
        return new ResponseEntity<Optional<Restaurant>>(restaurantService.singleRestaurant(imdbId),HttpStatus.OK);
    }

    @GetMapping("/{imdbId}/reviews")
    public ResponseEntity<List<Review>> getReviewsForRestaurant(@PathVariable String imdbId) {
        List<Review> reviews = restaurantService.getReviewsForRestaurant(imdbId);
        if (reviews != null) {
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{imdbId}/sentiments")
    public ResponseEntity<List<Map<String, Object>>> getSentimentsForRestaurant(@PathVariable String imdbId) {
        System.out.println("Fetching reviews for IMDb ID: " + imdbId);
        List<Review> reviews = restaurantService.getReviewsForRestaurant(imdbId);
        if (reviews != null) {
            System.out.println("Reviews found: " + reviews.size());
            List<Map<String, Object>> sentiments = reviews.stream().map(review -> {
                Map<String, Object> sentimentDetails = new HashMap<>();
                sentimentDetails.put("reviewBody", review.getBody());
                sentimentDetails.put("sentiment", review.getSentiment());
                sentimentDetails.put("score", review.getScore());
                return sentimentDetails;
            }).toList();
            return new ResponseEntity<>(sentiments, HttpStatus.OK);
        } else {
            System.out.println("No reviews found for IMDb ID: " + imdbId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
