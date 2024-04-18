package de.rollocraft.lobbySystem.Minecraft.Objects;

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
    public String toString() {
        if (days == 0 && hours == 0 ) {
            return String.format(" %d m, %d sec", minutes, seconds);
        }
        if (days == 0) {
            return String.format("%d h, %d m, %d sec", hours, minutes, seconds);
        }
        return String.format("%d d, %d h, %d m, %d sec", days, hours, minutes, seconds);
    }

    public Time add(Time other) {
        int totalDays = this.days + other.days;
        int totalHours = this.hours + other.hours;
        int totalMinutes = this.minutes + other.minutes;
        int totalSeconds = this.seconds + other.seconds;

        if (totalSeconds >= 60) {
            totalMinutes += totalSeconds / 60;
            totalSeconds = totalSeconds % 60;
        }

        if (totalMinutes >= 60) {
            totalHours += totalMinutes / 60;
            totalMinutes = totalMinutes % 60;
        }

        if (totalHours >= 24) {
            totalDays += totalHours / 24;
            totalHours = totalHours % 24;
        }

        return new Time(totalDays, totalHours, totalMinutes, totalSeconds);
    }

    public Time subtract(Time other) {
        int totalDays = this.days - other.days;
        int totalHours = this.hours - other.hours;
        int totalMinutes = this.minutes - other.minutes;
        int totalSeconds = this.seconds - other.seconds;

        if (totalSeconds < 0) {
            totalMinutes--;
            totalSeconds += 60;
        }

        if (totalMinutes < 0) {
            totalHours--;
            totalMinutes += 60;
        }

        if (totalHours < 0) {
            totalDays--;
            totalHours += 24;
        }

        // Wenn die Tage negativ sind, setzen wir sie auf 0
        if (totalDays < 0) {
            totalDays = 0;
        }

        return new Time(totalDays, totalHours, totalMinutes, totalSeconds);
    }
}

