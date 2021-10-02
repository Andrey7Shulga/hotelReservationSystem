package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;

public class AdminResource {

    private static AdminResource INSTANCE;
    public static final CustomerService customerService = CustomerService.getINSTANCE();
    public static final ReservationService reservationService = ReservationService.getINSTANCE();

    private AdminResource() {}

    public static AdminResource getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (AdminResource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AdminResource();
                }
            }
        }
        return INSTANCE;
    }


    public static Customer getCustomer (String email) {
        return customerService.getCustomer(email);
    }

    public static void addRoom (IRoom room) {
        reservationService.addRoom(room);
    }

    public static Collection<IRoom> getAllRooms () {
        return reservationService.getAllRooms();
    }

    public static Collection<Customer> getAllCustomers () {
        return customerService.getAllCustomers();
    }

    public static void displayAllReservations () {
        reservationService.printAllReservation();
    }

    public static Collection<Reservation> getReservations () {
        return reservationService.getAllReservations();
    }
}
