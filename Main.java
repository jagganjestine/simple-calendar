package com.calendarapp;

public class Main {
    public static void main(String[] args) {
        CalendarManager calendarManager = new CalendarManager();
        UserInterface userInterface = new UserInterface(calendarManager);
        userInterface.start();
    }
}
