package model;

import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public final Customer getCustomer() {
        return customer;
    }

    public final void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public final IRoom getRoom() {
        return room;
    }

    public final void setRoom(IRoom room) {
        this.room = room;
    }

    public final Date getCheckInDate() {
        return checkInDate;
    }

    public final void setCheckInDate(Date checkIn) {
        this.checkInDate = checkIn;
    }

    public final Date getCheckOutDate() {
        return checkOutDate;
    }

    public final void setCheckOutDate(Date checkOut) {
        this.checkOutDate = checkOut;
    }

    @Override
    public String toString() {
        return "Customer: " + customer.toString() + ", Room: " + room.toString() + ", CheckInDate: " + checkInDate + ", CheckOutDate: " + checkOutDate;
    }
}
