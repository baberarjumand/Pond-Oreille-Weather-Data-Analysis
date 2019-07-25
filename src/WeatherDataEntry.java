import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class WeatherDataEntry {

        private LocalDate date;
        private LocalTime time;
        private double pressure;

    public WeatherDataEntry(LocalDate date, LocalTime time, double pressure) {
        this.date = date;
        this.time = time;
        this.pressure = pressure;
    }

    public WeatherDataEntry(LocalDateTime ldt) {
        this.date = ldt.toLocalDate();
        this.time = ldt.toLocalTime();
        this.pressure = 0;
    }

    @Override
    public String toString() {
        return "Date: " + date.toString() + " Time: " + time.toString() + " Pressure: " + pressure;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public double getPressure() {
        return pressure;
    }

    public LocalDateTime getDateTime() {
        return LocalDateTime.of(date, time);
    }

    @Override
    public boolean equals(Object obj) {
        // it is assumed that a data point exists at least at every 15 min interval
        // this function checks +/- 15 minutes of the current object minute being compared
        for (int i = 0; i < 15 ; i++) {
            if ( this.date.compareTo(((WeatherDataEntry)obj).getDate())==0 &&
                    this.time.getHour()==(((WeatherDataEntry)obj).getTime().getHour()) &&
                    (this.time.getMinute()+i)==(((WeatherDataEntry)obj).getTime().getMinute()) ) {
                return true;
            } else if ( this.date.compareTo(((WeatherDataEntry)obj).getDate())==0 &&
                    this.time.getHour()==(((WeatherDataEntry)obj).getTime().getHour()) &&
                    (this.time.getMinute()-i)==(((WeatherDataEntry)obj).getTime().getMinute()) ) {
                return true;
            }
        }
        return false;
    }
}
