package com.oubaid.Restaurants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/withOrganizers")
    public List<EventWithOrganizers> getAllEventsWithOrganizers() {
        return eventService.getAllEventsWithOrganizers();
    }

    @PostMapping("/{eventId}/subscribe")
    public Event subscribeToEvent(@PathVariable String eventId, @RequestBody Subscriber subscriber) {
        return eventService.subscribeToEvent(eventId, subscriber);
    }

    @DeleteMapping("/{eventId}/unsubscribe")
    public Event unsubscribeFromEvent(@PathVariable String eventId, @RequestParam String email) {
        return eventService.unsubscribeFromEvent(eventId, email);
    }
}
