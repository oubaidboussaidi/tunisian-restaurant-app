package com.oubaid.Restaurants;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "events")
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    private String id;
    private String name;
    private String date;
    private String image;
    private List<String> traditionalFoods;
    private List<Subscriber> subscribers;
    private List<String> organizer;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public List<String> getTraditionalFoods() { return traditionalFoods; }
    public void setTraditionalFoods(List<String> traditionalFoods) { this.traditionalFoods = traditionalFoods; }

    public List<Subscriber> getSubscribers() { return subscribers; }
    public void setSubscribers(List<Subscriber> subscribers) { this.subscribers = subscribers; }

    public List<String> getOrganizers() { return organizer; }
    public void setOrganizers(List<String> organizers) { this.organizer = organizers; }
}
