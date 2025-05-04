package com.oubaid.Restaurants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public List<EventWithOrganizers> getAllEventsWithOrganizers() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("restaurants", "organizer", "imdbId", "organizerDetails"),
                Aggregation.project("name", "date", "image", "traditionalFoods", "subscribers", "organizers")
                        .and("organizerDetails.title").as("organizerNames")
        );

        AggregationResults<EventWithOrganizers> results = mongoTemplate.aggregate(
                aggregation, "events", EventWithOrganizers.class
        );

        return results.getMappedResults();
    }

    public Event subscribeToEvent(String eventId, Subscriber subscriber) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            if (!event.getSubscribers().stream().anyMatch(sub -> sub.getEmail().equals(subscriber.getEmail()))) {
                event.getSubscribers().add(subscriber);
                return eventRepository.save(event);
            }
        }
        return null;
    }

    public Event unsubscribeFromEvent(String eventId, String userEmail) {
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.getSubscribers().removeIf(sub -> sub.getEmail().equals(userEmail));
            return eventRepository.save(event);
        }
        return null;
    }
}
