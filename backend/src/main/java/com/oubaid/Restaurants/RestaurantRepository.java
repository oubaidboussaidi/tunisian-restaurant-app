package com.oubaid.Restaurants;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends MongoRepository<Restaurant, Object> {
    Optional<Restaurant> findRestaurantByImdbId(String imdbId);
}
