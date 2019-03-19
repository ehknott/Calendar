import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.*;

/*Takes in the standard format as designated by the input section, i.e. 2018-05-02,2018-12-31,Wednesday in a csv file
    but also takes in start/end dates(inclusive bounds), after the initial input, that designate blackout sections.
    I.e. holidays and vacations to factor into
    how many vacation days there are.
 */
public class SecondCalculator implements CalendarCalculate{
    List<Calendar> start;
    List<Calendar> end;
    List<DayOfWeek> day;
    List<List<Calendar[]>> blackoutDays;
    String file;

    public SecondCalculator(String file){
        this.file = file;
        start = new ArrayList<Calendar>();
        end = new ArrayList<Calendar>();
        day = new ArrayList<DayOfWeek>();
        blackoutDays = new ArrayList<List<Calendar[]>>();
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
                ArrayList<Calendar[]> blackoutperiod = new ArrayList<Calendar[]> ();
                for(int i = 3; i < splitline.length; i+=2) {
                    Calendar[] timeperiod = {dateMaker(splitline[i]), dateMaker(splitline[i+1])};
                    blackoutperiod.add(timeperiod);

                }
                if(!(blackoutperiod.size() == 0)) {
                    blackoutDays.add(sortAndCombine(blackoutperiod));
                }
                else{
                    blackoutDays.add(blackoutperiod);
                }
                line = filereader.readLine();
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }

    private int calCompare(Calendar o1, Calendar o2){
        if(o1.get(Calendar.YEAR) < o2.get(Calendar.YEAR)){
            return -1;
        }
        else if(o2.get(Calendar.YEAR) < o1.get(Calendar.YEAR)){
            return 1;
        }
        else{
            if (o1.get(Calendar.MONTH) < o2.get(Calendar.MONTH)){
                return -1;
            }
            else if (o2.get(Calendar.MONTH) < o1.get(Calendar.MONTH)) {
                return 1;
            }
            else{
                if(o1.get(Calendar.DAY_OF_MONTH) < o2.get(Calendar.DAY_OF_MONTH)) {
                    return -1;
                }
                else if(o2.get(Calendar.DAY_OF_MONTH) < o1.get(Calendar.DAY_OF_MONTH)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    private ArrayList<Calendar[]> sortAndCombine(ArrayList<Calendar[]> blackoutperiods){
        ArrayList<Calendar[]> sorted = new ArrayList<Calendar[]>();
        blackoutperiods.sort(new Comparator<Calendar[]>() {
            @Override
            public int compare(Calendar[] o1arr, Calendar[] o2arr) {
                Calendar o1 = o1arr[0];
                Calendar o2 = o2arr[0];
                return calCompare(o1, o2);
            }
        });
        int loc = 1;
        Calendar[] curr;
        sorted.add(blackoutperiods.get(0));
        while(loc < blackoutperiods.size()) {
            Calendar[] recent = sorted.get(sorted.size()-1);
            curr = blackoutperiods.get(loc);
            if(calCompare(curr[0],recent[1]) <= 0){
                recent[1] = curr[1];
            }
            else{
                sorted.add(curr);
            }
            loc++;
        }
        return sorted;
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
        if(end.get(Calendar.DAY_OF_WEEK) == day.getValue()){
            res++;
        }
        return res;
    }

    public String printNumMeetings(){
        String res = "";
        for(int i = 0; i < start.size(); i++){
            res = res+ "Number of meetings from " + start.get(i).getTime().toString() +" to " + end.get(i).getTime().toString() + " on " + day.get(i).toString() + " minus blackout dates: ";
            int numMeet = numMeetings(start.get(i), end.get(i), day.get(i));
            List<Calendar[]> blackoutPeriod = blackoutDays.get(i);
            for(int x = 0; x < blackoutPeriod.size(); x+=2){
                numMeet -= numMeetings(blackoutPeriod.get(x)[0], blackoutPeriod.get(x)[1], day.get(i));
            }
            res = res + numMeet + "\n";
        }
        System.out.println(res);
        return res;

    }
}
