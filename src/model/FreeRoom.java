package model;

public class FreeRoom extends Room {
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, (double) 0, roomType);
    }

    public String toString() {
        return "Free Room - Room Number: " + getRoomNumber() + ", Room Type: " + getRoomType() + ", Room Price:" + getRoomPrice();
    }
}
