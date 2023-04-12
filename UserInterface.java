package com.calendarapp;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class UserInterface {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");

    private CalendarManager calendarManager;

    public UserInterface(CalendarManager calendarManager) {
        this.calendarManager = calendarManager;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        // Display the calendar view when the application starts up
        viewCalendar();

        do {
            System.out.println("Calendar Application");
            System.out.println("--------------------");
            System.out.println("1. Add Event");
            System.out.println("2. Remove Event");
            System.out.println("3. Update Event");
            System.out.println("4. View Events for Date");
            System.out.println("5. View All Events");
            System.out.println("6. View Calendar");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addEvent(scanner);
                    break;
                case 2:
                    removeEvent(scanner);
                    break;
                case 3:
                    updateEvent(scanner);
                    break;
                case 4:
                    viewEventsForDate(scanner);
                    break;
                case 5:
                    viewAllEvents();
                    break;
                case 6:
                    viewCalendar();
                    break;
                case 7:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.println();
        } while (choice != 7);
    }


    private void addEvent(Scanner scanner) {
        scanner.nextLine(); // Consume the newline character after reading the choice
        System.out.println("Add Event");
        System.out.println("---------");
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter date (MM/DD/YYYY): ");
        String dateString = scanner.nextLine();
        System.out.print("Enter start time (HH:MM AM/PM): ");
        String startTimeString = scanner.nextLine();
        System.out.print("Enter end time (HH:MM AM/PM): ");
        String endTimeString = scanner.nextLine();

        try {
            LocalDateTime startTime = LocalDateTime.parse(dateString + " " + startTimeString, DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a"));
            LocalDateTime endTime = LocalDateTime.parse(dateString + " " + endTimeString, DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a"));

            Event event = new Event(title, startTime, endTime);
            calendarManager.addEvent(event);
            System.out.println("Event added successfully.");
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private void removeEvent(Scanner scanner) {
        scanner.nextLine(); // Consume the newline character after reading the choice
        System.out.println("Remove Event");
        System.out.println("------------");
        System.out.print("Enter event title: ");
        String title = scanner.nextLine();

        Event event = findEventByTitle(title);
        if (event != null) {
            calendarManager.removeEvent(event);
            System.out.println("Event removed successfully.");
        } else {
            System.out.println("Event not found.");
        }
    }

    private void updateEvent(Scanner scanner) {
        scanner.nextLine(); // Consume the newline character after reading the choice
        System.out.println("Update Event");
        System.out.println("------------");
        System.out.print("Enter event title: ");
        String title = scanner.nextLine();

        Event oldEvent = findEventByTitle(title);
        if (oldEvent != null) {
            System.out.print("Enter new title (or leave blank to keep the existing title): ");
            String newTitle = scanner.nextLine();
            System.out.print("Enter new date (MM/DD/YYYY) (or leave blank to keep the existing date): ");
            String dateString = scanner.nextLine();
            System.out.print("Enter new start time (HH:MM AM/PM) (or leave blank to keep the existing start time): ");
            String startTimeString = scanner.nextLine();
            System.out.print("Enter new end time (HH:MM AM/PM) (or leave blank to keep the existing end time): ");
            String endTimeString = scanner.nextLine();

            try {
                LocalDateTime startTime = oldEvent.getStartTime();
                LocalDateTime endTime = oldEvent.getEndTime();

                if (!newTitle.isBlank()) {
                    oldEvent.setTitle(newTitle);
                }
                if (!dateString.isBlank()) {
                    startTime = LocalDateTime.parse(dateString + " " + startTimeString, DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a"));
                    endTime = LocalDateTime.parse(dateString + " " + endTimeString, DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a"));
                }
                if (!startTimeString.isBlank()) {
                    startTime = LocalDateTime.parse(dateString + " " + startTimeString, DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a"));
                }
                if (!endTimeString.isBlank()) {
                    endTime = LocalDateTime.parse(dateString + " " + endTimeString, DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a"));
                }

                Event newEvent = new Event(oldEvent.getTitle(), startTime, endTime);
                calendarManager.updateEvent(oldEvent, newEvent);
                System.out.println("Event updated successfully.");
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        } else {
            System.out.println("Event not found.");
        }
    }

    private void viewEventsForDate(Scanner scanner) {
        scanner.nextLine(); // Consume the newline character after reading the choice
        System.out.println("View Events for Date");
        System.out.println("--------------------");
        System.out.print("Enter date (MM/DD/YYYY): ");
        String dateString = scanner.nextLine();

        try {
            LocalDate date = LocalDate.parse(dateString, DATE_FORMATTER);
            System.out.println("Events for " + dateString + ":");
            for (Event event : calendarManager.getEventsForDate(date)) {
                System.out.println("- " + event.getTitle() + " (" + event.getStartTime().format(TIME_FORMATTER) + " - " + event.getEndTime().format(TIME_FORMATTER) + ")");
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    private void viewAllEvents() {
        System.out.println("All Events");
        System.out.println("----------");
        calendarManager.displayAllEvents();
    }

    private void viewCalendar() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());

        System.out.println("Calendar for " + today.getMonth() + " " + today.getYear());
        System.out.println("+---+---+---+---+---+---+---+");

        // Print the day labels
        System.out.print("|");
        for (DayOfWeek day : DayOfWeek.values()) {
            System.out.print(day.getDisplayName(TextStyle.SHORT, Locale.US) + "|");
        }
        System.out.println();

        // Print the calendar dates for each week
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        for (int i = 1; i < dayOfWeek; i++) {
            System.out.print("|   ");
        }
        for (LocalDate date = firstDayOfMonth; date.isBefore(lastDayOfMonth.plusDays(1)); date = date.plusDays(1)) {
            System.out.print("| " + String.format("%1$2s", date.getDayOfMonth()) + " ");
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                System.out.println("|");
            }
        }
        int remainingDays = 7 - lastDayOfMonth.getDayOfWeek().getValue();
        for (int i = 0; i < remainingDays; i++) {
            System.out.print("|   ");
        }
        System.out.println("|");
        System.out.println("+---+---+---+---+---+---+---+");
    }


    private Event findEventByTitle(String title) {
        for (Event event : calendarManager.getAllEvents()) {
            if (event.getTitle().equals(title)) {
                return event;
            }
        }
        return null;
    }

}