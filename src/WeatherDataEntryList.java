import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class WeatherDataEntryList {

    private ArrayList<WeatherDataEntry> dataObjectsList = new ArrayList<>();

    public WeatherDataEntryList(String[] inputFiles) {
        System.out.println("Processing input files...");
        for (String s: inputFiles) {
            addObjectsToListFromFile(s);
            System.out.println("Processed " + s);
        }
        System.out.println(dataObjectsList.size() + " entries stored in list between " +
                dataObjectsList.get(0).getDate() +
                "  and " +
                dataObjectsList.get(dataObjectsList.size()-1).getDate());
    }

    private void addObjectsToListFromFile (String inputFile) {

        try (Scanner s = new Scanner(new FileReader(inputFile))) {
            s.nextLine(); // skips first line of input text file
            while(s.hasNext()) {
                // get one line of data from text file and split it into tokens
                String[] dataLine = s.nextLine().split("\t");

                // make date and time objects from first token
                String[] dateAndTime = dataLine[0].split(" ");
                LocalDate lDate = LocalDate.parse(dateAndTime[0].replace('_','-'));
                LocalTime lTime = LocalTime.parse(dateAndTime[1]);

                // grab pressure data token
                double pressure = Double.parseDouble(dataLine[2]);

                // create WeatherDataEntry object and add to list
                dataObjectsList.add(new WeatherDataEntry(lDate, lTime, pressure));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public double calculateSlopeBetweenTwoDatesAndTimes (LocalDateTime ldt1, LocalDateTime ldt2) {
        double slope=0f;
        LocalDateTime listStartDate = dataObjectsList.get(0).getDateTime();
        LocalDateTime listEndDate = dataObjectsList.get(dataObjectsList.size()-1).getDateTime();

        if ( ldt1.compareTo(listStartDate)>=0 && ldt2.compareTo(listEndDate)<=0 ) {
            System.out.println("Provided data range (" + ldt1.toLocalDate() + " " + ldt1.toLocalTime() + " and " + ldt2.toLocalDate() + " " + ldt2.toLocalTime() +  ") is valid");

            WeatherDataEntry data1 = getClosestDataEntry(ldt1);
            System.out.println("Closest SData found: " + data1);
            WeatherDataEntry data2 = getClosestDataEntry(ldt2);
            System.out.println("Closest EData found: " + data2);

            return (data2.getPressure() - data1.getPressure());

        } else {
            System.out.println("Provided dates are out of current data's date and time range");
        }

        return slope;
    }

    private WeatherDataEntry getClosestDataEntry(LocalDateTime ldt) {

        WeatherDataEntry w = new WeatherDataEntry(ldt);
        for(WeatherDataEntry w1: dataObjectsList){
            if(w.equals(w1))
                return w1;
        }
        return null;
    }
}
