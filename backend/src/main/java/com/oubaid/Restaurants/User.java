package com.oubaid.Restaurants;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private ObjectId id;
    @Setter
    private String username;
    @Setter
    private String email;
    @Setter
    private String password;
    @Setter
    private List<String> favoriteRestaurants= Collections.emptyList();
    @Setter
    private Map<String, String> restaurantReviews=Collections.emptyMap();

    public ObjectId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getFavoriteRestaurants() {
        return favoriteRestaurants;
    }

    public Map<String, String> getRestaurantReviews() {
        return restaurantReviews;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
