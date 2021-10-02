package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {

    private static ReservationService INSTANCE;
    private Set<IRoom> rooms = new HashSet<>();
    private Collection<Reservation> reservations = new HashSet<>();

    private ReservationService() {}

    public static ReservationService getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (ReservationService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ReservationService();
                }
            }
        }
        return INSTANCE;
    }

    public void addRoom (IRoom room) {
        rooms.add(room);
    }

    public IRoom getARoom (String roomID) {
            for (IRoom ir : rooms) {
                if (ir.getRoomNumber().equals(roomID)) {
                    return ir;
                }
            }
        System.out.printf("There is no room with number %s, choose another one%n", roomID);
        return null;
    }

    public Reservation reserveARoom (Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation reserveForCustomer = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reserveForCustomer);
        System.out.println("The room has been reserved");
        return reserveForCustomer;
    }

    public Collection<IRoom> findRooms (Date checkInDate, Date checkOutDate) {
        Set<IRoom> roomsAvailable = new HashSet<>();
        System.out.println("Rooms list you may chose from: " + rooms);

        ///check out if the current reservation is empty
        if (reservations.size() == 0) {
            roomsAvailable = rooms;
            return roomsAvailable;
        } else {
            for (Reservation r : reservations) {
                for (IRoom iRoom : rooms) {
                    if ((iRoom.getRoomNumber().equals(r.getRoom().getRoomNumber()))
                                    &&
                            ((checkInDate.before(r.getCheckInDate()) && checkOutDate.before(r.getCheckInDate()))
                                    ||
                            (checkInDate.after(r.getCheckOutDate()) && checkOutDate.after(r.getCheckOutDate())))
                                    ||
                            (!r.getRoom().getRoomNumber().contains(iRoom.getRoomNumber()))) {
                        roomsAvailable.add(iRoom);
                    } else if (iRoom.getRoomNumber().equals(r.getRoom().getRoomNumber())) {
                        roomsAvailable.remove(iRoom);
                    }
                }
            }
        }
        fixOutput(roomsAvailable, checkInDate, checkOutDate);
        return roomsAvailable;
    }

    private void fixOutput(Collection<IRoom> availableRooms,Date checkInDate, Date checkOutDate) {
        for (Reservation res : reservations) {
            for (IRoom room : rooms) {
                if (room.getRoomNumber().equals(res.getRoom().getRoomNumber())
                        && !((checkInDate.before(res.getCheckInDate()) && checkOutDate.before(res.getCheckInDate()))
                        || (checkInDate.after(res.getCheckOutDate()) && checkOutDate.after(res.getCheckOutDate()))))
                    availableRooms.remove(room);
            }
        }
    }

    public Collection<Reservation> getCustomerReservation (Customer customer) {
        return  getCustomerReservationDefault(customer);
    }

    Collection<Reservation> getCustomerReservationDefault (Customer customer) {
        ArrayList<Reservation> newList = new ArrayList<>();
        for (Reservation res : reservations) {
            if (res.getCustomer().equals(customer)) {
                newList.add(res);
            }
        }
        return newList;
    }

    public void printAllReservation() {
        for (Reservation res : reservations) {
            System.out.println(res);
        }
    }

    public Collection<Reservation> getAllReservations () {
        return reservations;
    }

    public Collection<IRoom> getAllRooms () {
        return rooms;
    }

}
