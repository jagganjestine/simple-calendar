package com.calendarapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarManager {
    private List<Event> events;

    public CalendarManager() {
        events = new ArrayList<>();
    }

    // Add a new event
    public void addEvent(Event event) {
        events.add(event);
    }

    // Remove an event
    public void removeEvent(Event event) {
        events.remove(event);
    }

    // Update an existing event
    public void updateEvent(Event oldEvent, Event newEvent) {
        int index = events.indexOf(oldEvent);
        if (index >= 0) {
            events.set(index, newEvent);
        }
    }

    // Get events for a specific date
    public List<Event> getEventsForDate(LocalDate date) {
        return events.stream()
                .filter(event -> event.getStartTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    // Get all events
    public List<Event> getAllEvents() {
        return events;
    }

    // Display all events
    public void displayAllEvents() {
        if (events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }
}
