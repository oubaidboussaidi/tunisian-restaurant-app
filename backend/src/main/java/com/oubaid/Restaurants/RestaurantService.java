package com.oubaid.Restaurants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    @Autowired
    public RestaurantRepository restaurantRepository;

    public List<Restaurant> allRestaurants(){
        return restaurantRepository.findAll();
    }

    public Optional<Restaurant> singleRestaurant(String imdbId){
        return restaurantRepository.findRestaurantByImdbId(imdbId);
    }

    public List<Review> getReviewsForRestaurant(String imdbId) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findRestaurantByImdbId(imdbId);
        if (restaurantOptional.isPresent()) {
            return restaurantOptional.get().getReviewIds();
        }
        return null;
    }
}
