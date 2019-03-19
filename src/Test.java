import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Test {

    public static void main(String[] args){
        MVPCalculator tests = new MVPCalculator("./src/resources/test1.csv");
        tests.printNumMeetings();
        SecondCalculator test2 = new SecondCalculator("./src/resources/test2.csv");
        test2.printNumMeetings();
    }
}
