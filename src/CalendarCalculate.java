import java.time.DayOfWeek;
import java.util.Calendar;

/**
 * Basic calender calculating interface..user can add on extra functionality. Need
 * the basic function to read in the file/data and then another function that
 * calculates the number of meetings
 * i.e. extra functionality can include taking in extra dates, taking out sections of time for vacation/holidays
 */
public interface CalendarCalculate {

    //reads in data to organize into a usable format
    void readInFile();
    //calculates number of meetings
    int numMeetings(Calendar start, Calendar end, DayOfWeek day);
}
