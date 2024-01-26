package service;

import model.Customer;
import model.IRoom;
import model.Reservation;
import model.Room;

import java.util.*;

public class ReservationService {
    private static final ReservationService reference = new ReservationService();

    private final Set<IRoom> availableRooms;
    private final Set<Reservation> reservations;

    private ReservationService() {
        this.availableRooms = new HashSet<IRoom>();
        this.reservations = new HashSet<Reservation>();
    }

    public final Set<Reservation> getReservations() {
        return reservations;
    }

    public final Set<IRoom> getAvailableRooms() {
        return availableRooms;
    }

    public final void addRoom(IRoom room) {
        for (IRoom r : availableRooms) {
            if (r.equals(room)) {
                throw new IllegalArgumentException("This room number already exists, please add a room with a different room number.");
            }
        }
        availableRooms.add(room);
    }

    public final IRoom getARoom(String roomId) {
        for(IRoom room: getAvailableRooms()) {
            if(room.getRoomNumber().equals(roomId)) {
                return room;
            }
        }
        return null;
    }

    public final Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(newReservation);
        return newReservation;
    }

    public final Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Set<IRoom> freeRooms = new HashSet<IRoom>();
        Set<IRoom> reservedRooms = getReservedRooms(
                checkInDate, checkOutDate);

        for(IRoom room: getAvailableRooms()) {
            if(!reservedRooms.contains(room)) {
                freeRooms.add(room);
            }
        }

        return freeRooms;
    }

    public final Collection<IRoom> findAlternativeRooms(Date checkInDate, Date checkOutDate) {
        return findRooms(add7days(checkInDate), add7days(checkOutDate));
    }

    public final Date add7days(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        Date newDate = calendar.getTime();

        return newDate;
    }

    public final Set<IRoom> getReservedRooms(Date checkInDate, Date checkOutDate) {
        Set<IRoom> reservedRooms = new HashSet<IRoom>();
        for(Reservation r: getReservations()) {
            IRoom room = r.getRoom();
            if((checkInDate.before(r.getCheckOutDate()) && checkOutDate.after(r.getCheckInDate()))
               || checkInDate.equals(r.getCheckInDate()) || checkOutDate.equals(r.getCheckOutDate())) {
                reservedRooms.add(room);
            }
        }
        return reservedRooms;
    }

    public final Collection<Reservation> getCustomersReservation(Customer customer) {
        Set<Reservation> customerReservation = new HashSet<Reservation>();
        for(Reservation r: getReservations()) {
            if(r.getCustomer().equals(customer)) {
                customerReservation.add(r);
            }
        }

        return customerReservation;
    }

    public final void printAllReservation() {
        for(Reservation r: getReservations()) {
            System.out.println(r);
        }
    }

    public static ReservationService getInstance() {
        return reference;
    }
}
