package com.oubaid.Restaurants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String SENTIMENT_ANALYSIS_URL = "http://127.0.0.1:8000/analyze";

    public Review createReview(String reviewBody, String imdbId) {
        Review review = new Review(reviewBody);

        Map<String, Object> sentimentData = analyzeSentiment(reviewBody);
        review.setSentiment(sentimentData.get("sentiment").toString());
        review.setScore(Double.parseDouble(sentimentData.get("score").toString())); // Set the score

        reviewRepository.insert(review);

        mongoTemplate.update(Restaurant.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review;
    }

    private Map<String, Object> analyzeSentiment(String reviewBody) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> request = new HashMap<>();
        request.put("text", reviewBody);
        Map<String, Object> sentimentData = new HashMap<>();

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(SENTIMENT_ANALYSIS_URL, request, Map.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                sentimentData = response.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sentimentData;
    }
}
