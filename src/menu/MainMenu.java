package menu;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        actions();
    }

    public static void actions() {
        //display the main menu
        displayMainMenu();
        int userSelection = 0;

        do {
            try {
                System.out.println("Please, enter a digit (from 1 to 5) of your choice:");
                userSelection = scanner.nextInt();
                switch (userSelection) {
                    case 1 -> {
                        System.out.println("You selected: \"Find and reserve a room\"");
                        findAndReserveARoom();
                    }
                    case 2 -> {
                        System.out.println("You selected: \"See my reservations\"");
                        displayCustomerReservations();
                    }
                    case 3 -> {
                        System.out.println("You selected: \"Create an account\"");
                        createAnAccount();
                    }
                    case 4 -> {
                        System.out.println("You selected: \"Admin\"");
                        AdminMenu.start();
                    }
                    case 5 -> {
                        System.out.println("You selected: \"Exit\"");
                        scanner.close();
                        System.exit(0);
                    }
                    default -> System.out.println("Your choice is not correct: '" + userSelection + "'");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid choice. ");
            }
            scanner.nextLine();
        } while (userSelection <= 0 || userSelection > 5);
    }

    public static void displayMainMenu () {
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("Welcome to CRS Hotels Reservation System. Please, enter a digit of your choice:");
        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("1 - Find and reserve a room");
        System.out.println("2 - See my reservations");
        System.out.println("3 - Create an account");
        System.out.println("4 - Admin");
        System.out.println("5 - Exit");
    }


    private static void findAndReserveARoom () {
        //add new variables, formatter from String input to Date and Scanner
        Date checkIn, checkOut, checkInNew, checkOutNew;
        IRoom room;
        Collection<IRoom> roomResult;
        String roomChosen, email;
        Optional<IRoom> ass;

        //Start communication
        //CheckIn Date
        checkIn = Helper.storeADate("in", scanner);
        //CheckOut Date
        checkOut = Helper.storeADate("out", scanner);

        //Display all the available rooms to make a reservation
         roomResult = HotelResource.findARoom(checkIn, checkOut);

            if (!roomResult.isEmpty()) {
                //display all the rooms available
                System.out.println("Available rooms for your period of time chosen:");
                    for (IRoom i : roomResult) {
                        System.out.println(i);
                    }

                //Choosing a correct room
                System.out.println("Please, enter a preferred room number");
                    do {
                        roomChosen = scanner.next();
                        String finalRoomChosen = roomChosen;

                        ass = roomResult.stream().filter(s -> s.getRoomNumber().equals(finalRoomChosen)).findAny();
                            if (ass.isEmpty()) {
                                System.out.printf("There is no available room with number '%s', choose another one%n", roomChosen);
                            }
                        scanner.nextLine();
                    } while (ass.isEmpty());

                room = HotelResource.getRoom(roomChosen);
                System.out.println("Your choice is: " +  room);

                //Booking a room
                System.out.println("Please, enter a customer e-mail");
                do {
                    email = scanner.next();
                    if (HotelResource.getCustomer(email) != null) {
                        HotelResource.bookARoom(email, room, checkIn, checkOut);
                    } else {
                        System.out.printf("There is no customer with e-mail %s, type a correct e-mail%n", email);
                    }
                    scanner.nextLine();
                } while (HotelResource.getCustomer(email) == null);

                System.out.println("Your reservation has been created");
                Collection<Reservation> customerReservations = HotelResource.getCustomersReservations(email);
                System.out.println("Customer's reservations are:");
                for (Reservation r : customerReservations) {
                    System.out.println(r.getRoom());
                }

            } else {

                checkInNew = Helper.getNewDateForward(checkIn, 7);
                checkOutNew = Helper.getNewDateForward(checkOut, 7);
                roomResult = HotelResource.findARoom(checkInNew, checkOutNew);

                if (!roomResult.isEmpty()) {
                    //display all the rooms available within recommended period
                    System.out.println("There are not rooms available within the period chosen.");
                    System.out.printf("Recommended rooms available from '%s' to '%s'", Helper.dateAsString(checkInNew), Helper.dateAsString(checkOutNew));
                    System.out.println(":");
                        for (IRoom i : roomResult) {
                            System.out.println(i);
                        }
                } else {
                    System.out.println("------------------------------------------------------");
                    System.out.println("There are not rooms available within the period chosen and one week after.");
                    System.out.println("Please, try again or choose another option from the main menu");
                    System.out.println("----------------------------------------------------------s---");
                }
            }
        actions();
    }

//    private static boolean checkADate (String enteredDate) {
//        String dataRegEx = "^(0[1-9]|1[0-2])[\\/](0[1-9]|[12][\\d]|3[0-1])[\\/](20|21)\\d{2}$";
//        Pattern pattern = Pattern.compile(dataRegEx);
//        return pattern.matcher(enteredDate).matches();
//    }
//
//    private static Date storeADate (String inOut) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        String userInput;
//        Date date = new Date();
//
//        do {
//            System.out.println("Enter your check " + inOut + " date in mm/dd/yyyy format");
//            userInput = scanner.next();
//            if (!checkADate(userInput)) {
//                System.out.println("Your check " + inOut + " date is not correct: " + userInput);
//            }
//        } while (!checkADate(userInput));
//        try {
//            date = simpleDateFormat.parse(userInput);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return date;
//    }
//
//    static Date getNewDateForward (Date date, int numberOfDays) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.add(Calendar.DAY_OF_MONTH, numberOfDays);
//        return calendar.getTime();
//    }

    private static void displayCustomerReservations () {
        System.out.println("Please, enter a customer e-mail");
        String email = scanner.next();
        Collection<Reservation> col = HotelResource.getCustomersReservations(email);

        if (!col.isEmpty()) {
            System.out.println("Customer reservations:");
            for (Reservation res : col) {
                System.out.println(res);
            }
        } else {
            System.out.println("------------------------------------");
            System.out.println("Customer reservations list is empty!");
        }
        actions();
    }

    private static void createAnAccount () {
        String email;
        Collection<Customer> customerCollection = HotelResource.getAllCustomers();
        Optional<Customer> optCustomer;
        boolean isPresent;

        System.out.println("Please, enter the customer first name");
        String firstName = scanner.next();
        System.out.println("Please, enter the customer last name");
        String lastName = scanner.next();
        System.out.println("Please, enter a customer e-mail");

        do {
            email = scanner.next();
            String finalEmail = email;
            optCustomer = customerCollection.stream().filter(e -> (e.getEmail().equals(finalEmail))).findAny();

            if (optCustomer.isPresent()) {
                System.out.printf("Customer with e-mail '%s' is already exist, type another e-mail%n", email);
                isPresent = true;
            } else {
                HotelResource.createACustomer(firstName, lastName, email);
                isPresent = false;
            }
            scanner.nextLine();
        } while (HotelResource.getCustomer(email) == null || isPresent);

            System.out.println(HotelResource.getCustomer(email));
        actions();
    }
}
