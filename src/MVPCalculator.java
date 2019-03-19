import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Basic calculator for determining amount of meetings, write a CSV
 * file with the following type notation on each line: 2018-05-02,2018-12-31,Wednesday
 * to get the amount of meetings between two dates on a specific date
 */
public class MVPCalculator implements CalendarCalculate {
    List<Calendar> start;
    List<Calendar> end;
    List<DayOfWeek> day;
    String file;

    public MVPCalculator(String file){
        this.file = file;
        start = new ArrayList<Calendar>();
        end = new ArrayList<Calendar>();
        day = new ArrayList<DayOfWeek>();
        readInFile();
    }

    public void readInFile(){
        try {
            BufferedReader filereader = new BufferedReader(new FileReader(file));
            String line = filereader.readLine();
            while(line != null){
                String[] splitline = line.split(",");
                start.add(dateMaker(splitline[0]));
                end.add(dateMaker(splitline[1]));
                day.add(DayOfWeek.valueOf(splitline[2].toUpperCase()));
                line = filereader.readLine();
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private Calendar dateMaker(String date){
        String[] partsOfDate = date.split("-");
        return new GregorianCalendar(Integer.parseInt(partsOfDate[0]),
                Integer.parseInt(partsOfDate[1])-1,Integer.parseInt(partsOfDate[2]));
    }

    public int numMeetings(Calendar start, Calendar end, DayOfWeek day){
        int res = 0;
        while(!start.equals(end)){
            if(start.get(Calendar.DAY_OF_WEEK) == day.getValue()){
                res++;
            }
            start.add(Calendar.DAY_OF_YEAR, 1);
        }
        return res;
    }

    public String printNumMeetings(){
        String res = "";
        for(int i = 0; i < start.size(); i++){
            res = res+ "Number of meetings from " + start.get(i).getTime().toString() +" to " + end.get(i).getTime().toString() + " on " + day.get(i).toString() + " : ";
            int numMeet = numMeetings(start.get(i), end.get(i), day.get(i));
            res = res + numMeet + "\n";
        }
        System.out.println(res);
        return res;
    }

}
