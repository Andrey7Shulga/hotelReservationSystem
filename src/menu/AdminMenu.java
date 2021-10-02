package menu;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminMenu {

    private static final Scanner scanner = new Scanner(System.in);

    public static void start () {
        //display the admin menu
        displayAdminMenu();
        int userSelection = 0;
        do {
            try {
                System.out.println("Please, enter a digit (from 1 to 5) of your choice:");
                userSelection = scanner.nextInt();

                switch (userSelection) {
                    case 1 -> {
                        System.out.println("You selected: \"All Customer List\"");
                        Collection<Customer> customerList = AdminResource.getAllCustomers();
                        if (!customerList.isEmpty()) {
                            System.out.println("----------");
                            for (Customer c : customerList) {
                                System.out.println(c);
                            }
                        } else {
                            System.out.println("-----------------------");
                            System.out.println("Customer list is empty.");
                            System.out.println("-----------------------");
                        }
                        start();
                    }
                    case 2 -> {
                        System.out.println("You selected: \"All Room List\"");
                        Collection<IRoom> roomsList = AdminResource.getAllRooms();
                        if (!roomsList.isEmpty()) {
                            System.out.println("----------");
                            for (IRoom i : roomsList) {
                                System.out.println(i);
                            }
                        } else {
                            System.out.println("--------------------------------------------------------------");
                            System.out.println("Room list is empty. Add any room, opting '4' in the Admin menu");
                            System.out.println("--------------------------------------------------------------");
                        }
                        start();
                    }
                    case 3 -> {
                        System.out.println("You selected: \"All Reservations\"");
                        if (!AdminResource.getReservations().isEmpty()) {
                            AdminResource.displayAllReservations();
                        } else {
                            System.out.println("---------------------------");
                            System.out.println("Reservations list is empty.");
                            System.out.println("---------------------------");
                        }
                        start();
                    }
                    case 4 -> {
                        System.out.println("You selected: \"Add a Room\"");
                        addANewRoom();
                    }
                    case 5 -> {
                        System.out.println("You selected: \"Return to Main Menu\"");
                        MainMenu.actions();
                    }
                    default -> System.out.println("Your choice is not correct: '" + userSelection + "'");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid choice. ");
            }
            scanner.nextLine();
        } while (userSelection <= 0 || userSelection > 5);
    }

    public static void displayAdminMenu () {
        System.out.println("Admin Menu");
        System.out.println("----------");
        System.out.println("1 - All Customer List");
        System.out.println("2 - All Room List");
        System.out.println("3 - All Reservations");
        System.out.println("4 - Add a Room");
        System.out.println("5 - Return to Main Menu");
    }

    public static void addANewRoom(){

        String addAnotherRoom;

        do {
            RoomType roomType = null;
            int choiceRoomType = 0;
            double roomCost = 0.0;
            boolean isNumberExist = false;
            String roomID = "";

            //Enter a correct room's number
            do {
                Collection<IRoom> roomsList = AdminResource.getAllRooms();
                System.out.println("Enter a room's number you wish to add:");
                roomID = scanner.next();
                for (IRoom r : roomsList) {
                    if (r.getRoomNumber().equals(roomID)) {
                        System.out.println("There is an active room with this number - " + roomID);
                        isNumberExist = true;
                    } else isNumberExist = false;
                }
                scanner.nextLine();
            } while (isNumberExist);

            //Enter a correct room's price
            do {
                try {
                    System.out.println("Enter the room's price per night in $.C format");
                    roomCost = scanner.nextDouble();
                } catch (InputMismatchException e) {
                    System.out.print("Invalid choice. ");
                }
                scanner.nextLine();
            } while (roomCost <= 0.0);

            //Enter a correct room's type
            do {
                try {
                    System.out.println("Enter a room's type - 1 for 'Single' or 2 for 'Double'");
                    choiceRoomType = scanner.nextInt();
                    switch (choiceRoomType) {
                        case 1 -> {
                            roomType = RoomType.SINGLE;
                        }
                        case 2 -> {
                            roomType = RoomType.DOUBLE;
                        }
                        default -> System.out.println("Your choice is not correct: '" + choiceRoomType + "'");
                    }
                } catch (InputMismatchException e) {
                    System.out.print("Invalid choice. ");
                }
                scanner.nextLine();
            } while (choiceRoomType != 1 && choiceRoomType != 2);


            IRoom room = new Room(roomID, roomCost, roomType);
            AdminResource.addRoom(room);
            System.out.println("The room has been added: " + room);

            //Add another room question
            do {
                System.out.println("Would you like to add another room? y/n");
                addAnotherRoom = scanner.next().toLowerCase().trim();
            } while (!addAnotherRoom.equals("y") && !addAnotherRoom.equals("n"));

        } while (addAnotherRoom.equals("y"));

        start();
    }

}
