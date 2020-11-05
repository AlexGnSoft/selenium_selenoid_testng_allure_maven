package com.carespeak.domain.entities.common;

public enum Day {
    MONDAY(1, "Monday"),
    TUESDAY(2, "Tuesday"),
    WEDNESDAY(3, "Wednesday"),
    THURSDAY(4, "Thursday"),
    FRIDAY(5, "Friday"),
    SATURDAY(6, "Saturday"),
    SUNDAY(7, "Sunday");

    private int dayNumber;
    private String dayName;

    private Day(int dayNumber, String dayName) {
        this.dayNumber = dayNumber;
        this.dayName = dayName;
    }

    public String getDayName() {
        return dayName;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public static Day getDay(String dayName) {
        for (Day day : values()) {
            if (day.getDayName().toLowerCase().contains(dayName.toLowerCase())) {
                return day;
            }
        }
        throw new IllegalArgumentException("No day that matches '" + dayName + "' value!");
    }

    public static Day getDay(int dayNumber) {
        for (Day day : values()) {
            if (day.getDayNumber() == dayNumber) {
                return day;
            }
        }
        throw new IllegalArgumentException("No day that matches '" + dayNumber + "' value!");
    }

    public static Day[] getWorkingDays() {
        return new Day[]{MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY};
    }

    public static Day[] getWeekEnds() {
        return new Day[]{SATURDAY, SUNDAY};
    }

}
