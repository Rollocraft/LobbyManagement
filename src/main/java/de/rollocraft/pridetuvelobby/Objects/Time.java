package de.rollocraft.pridetuvelobby.Objects;

public class Time {
    private int days;
    private int hours;
    private int minutes;
    private int seconds;

    public Time(int days, int hours, int minutes, int seconds) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    // Getter für die Zeit
    public int getDays() {
        return days;
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    // Setter für die Zeit

    public void setTime(int days, int hours, int minutes, int seconds) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    // Methode zur Berechnung der Differenz zwischen zwei Zeitpunkten

    public Time difference(Time oldTime) {
        int diffDays = this.days - oldTime.days;
        int diffHours = this.hours - oldTime.hours;
        int diffMinutes = this.minutes - oldTime.minutes;
        int diffSeconds = this.seconds - oldTime.seconds;

        // Überprüfen und ggf. Anpassen der Zeitwerte, damit sie alle positiv sind
        if (diffSeconds < 0) {
            diffMinutes--;
            diffSeconds += 60;
        }
        if (diffMinutes < 0) {
            diffHours--;
            diffMinutes += 60;
        }
        if (diffHours < 0) {
            diffDays--;
            diffHours += 24;
        }

        return new Time(diffDays, diffHours, diffMinutes, diffSeconds);
    }


    @Override
    public String toString() {
        return String.format("%d Tage, %d Stunden, %d Minuten, %d Sekunden", days, hours, minutes, seconds);
    }
}

