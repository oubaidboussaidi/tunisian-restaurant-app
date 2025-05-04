package com.oubaid.Restaurants;

import java.util.List;

public class EventWithOrganizers {
    private String id;
    private String name;
    private String date;
    private String image;
    private List<String> traditionalFoods;
    private List<String> subscribers;
    private List<String> organizer;
    private List<String> organizerNames;

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

    public List<String> getSubscribers() { return subscribers; }
    public void setSubscribers(List<String> subscribers) { this.subscribers = subscribers; }

    public List<String> getOrganizers() { return organizer; }
    public void setOrganizers(List<String> organizers) { this.organizer = organizers; }

    public List<String> getOrganizerNames() { return organizerNames; }
    public void setOrganizerNames(List<String> organizerNames) { this.organizerNames = organizerNames; }
}
