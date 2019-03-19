import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String file = JOptionPane.showInputDialog(null,
                "Input CSV File Path " +
                        "(each line should be in file should be in form: 2018-05-02,2018-12-31,Wednesday)",
                "Input CSV File Path",1);
        Object[] options = {"Basic Calculator", "Extended Calculator", "Explain the calculators"};
        int n = JOptionPane.showOptionDialog(null,"Which function do you want to use?",
                "Date Calculator Selection",JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,null,options,0  );
        while(n == 2){
            JOptionPane.showMessageDialog(null,
                    "Basic: Just calculates number of meetings between two dates and on a specific day of the week \n" +
                            "Extended: Basic calculator that also allows you to input extra date sections after that are" +
                            " not considered in the calculations \n *Note: Dates are inclusive");
            n = JOptionPane.showOptionDialog(null,"Which function do you want to use?",
                    "Date Calculator Selection",JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,null,options,0  );
        }
        if(n == 0){
            MVPCalculator calc = new MVPCalculator(file);
            String res = calc.printNumMeetings();
            JOptionPane.showMessageDialog(null,res);
        }
        else if (n==1){
            SecondCalculator calc = new SecondCalculator(file);
            String res = calc.printNumMeetings();
            JOptionPane.showMessageDialog(null,res);
        }
    }
}
