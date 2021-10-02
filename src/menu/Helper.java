package menu;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Helper {

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    static Calendar calendar = Calendar.getInstance();


    private static boolean checkADate(String enteredDate) {
        String dataRegEx = "^(0[1-9]|1[0-2])[\\/](0[1-9]|[12][\\d]|3[0-1])[\\/](20|21)\\d{2}$";
        Pattern pattern = Pattern.compile(dataRegEx);
        return pattern.matcher(enteredDate).matches();
    }

    public static Date storeADate (String inOut, @NotNull Scanner scanner) {
        String userInput;
        Date date = new Date();

        do {
            System.out.println("Enter your check " + inOut + " date in mm/dd/yyyy format");
            userInput = scanner.next();
            if (!checkADate(userInput)) {
                System.out.println("Your check " + inOut + " date is not correct: " + userInput);
            }
        } while (!checkADate(userInput));
        try {
            date = simpleDateFormat.parse(userInput);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    static Date getNewDateForward (Date date, int numberOfDays) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, numberOfDays);
        return calendar.getTime();
    }

    static String dateAsString (Date date) {
        return simpleDateFormat.format(date);
    }



}
