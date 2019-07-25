/**
 * This program reads relevant weather data from provided input text files.
 * The data is weather data captured from Lake Pond Oreille in northern Idaho.
 * We have almost 20 megabytes of data from the years 2012 through 2015.
 * The slope coefficient of barometric pressure is calculated at two time intervals provided by user.
 * The sign (+,-,0) of the slope is used to predict upcoming weather conditions.
 *
 * This is the first project for the Java Code Clinic (by Carlos Rivas) on Lynda.com.
 *
 * Author:  Baber Arjumand
 * Date:    Jul 21 2019
 */

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) {

        // assumption is data is sorted by date and time in these files
        // so generated list below is also assumed to be sorted by date and time
        // no efforts have been made to sort this data

        // extract data from text files and create object that holds the list
        String[] inputFiles = {"files/Environmental_Data_Deep_Moor_2012.txt",
                "files/Environmental_Data_Deep_Moor_2013.txt",
                "files/Environmental_Data_Deep_Moor_2014.txt",
                "files/Environmental_Data_Deep_Moor_2015.txt"};
        WeatherDataEntryList wListObject = new WeatherDataEntryList(inputFiles);
        System.out.println();

        // calculate slope values by providing test cases
        System.out.println("Test Case #1");
        calculateSlope("2012-01-01", "00:30", "2012-01-01", "02:30", wListObject);

        System.out.println("Test Case #2");
        calculateSlope("2013-03-15", "10:30", "2013-03-17", "02:30", wListObject);

        System.out.println("Test Case #3");
        calculateSlope("2014-06-21", "10:00", "2014-06-25", "23:59", wListObject);

    }

    private static void calculateSlope(String startDate, String startTime, String endDate, String endTime, WeatherDataEntryList wListObject) {
        DecimalFormat f = new DecimalFormat("#0.000000");
        LocalDateTime ldt1 = LocalDateTime.of(LocalDate.parse(startDate), LocalTime.parse(startTime));
        LocalDateTime ldt2 = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.parse(endTime));
        double slope = wListObject.calculateSlopeBetweenTwoDatesAndTimes(ldt1,ldt2);
        System.out.println("The barometric pressure slope is: " + f.format(slope));
        if (slope>0) System.out.println("Fair and sunny weather imminent");
        if (slope==0) System.out.println("Weather likely to persist");
        if (slope<0) System.out.println("Stormy weather inbound");
        System.out.println();
    }
}
