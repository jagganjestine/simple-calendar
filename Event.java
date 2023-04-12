package com.calendarapp;

import java.time.LocalDateTime;

public class Event {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Event(String title, LocalDateTime startTime, LocalDateTime endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getter and setter methods for the title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and setter methods for the start time
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    // Getter and setter methods for the end time
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Event: " + title + ", Starts: " + startTime + ", Ends: " + endTime;
    }
}
