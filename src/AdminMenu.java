import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.*;

public class AdminMenu {

    private static AdminResource adminResource;
    public static final String[] options = new String[] {
        "1. See all customers",
        "2. See all rooms",
        "3. See all reservations",
        "4. Add a room",
        "5. Back to main menu"
    };

    public static void adminMenu() {

        adminResource = AdminResource.getInstance();

        Scanner scanner = new Scanner(System.in);
        int option = 1;

        while(option != 5) {
            printAdminMenu();

            try {
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        seeAllCustomers();
                        break;
                    case 2:
                        seeAllRooms();
                        break;
                    case 3:
                        seeAllReservations();
                        break;
                    case 4:
                        addARoom();
                        break;
                    case 5:
                        MainMenu.mainMenu();
                    default:
                        System.out.println("Error: Invalid input");
                }

            } catch (InputMismatchException ex) {
                System.out.println("Please enter an integer value between 1 and " + options.length);
                scanner.next();
            } catch (Exception ex) {
                System.out.println("An unexpected error happened. Please try again");
                scanner.next();
            }
        }
    }

    public static void seeAllCustomers() {
        Collection<Customer> customers = adminResource.getAllCustomers();
        if(customers == null || customers.isEmpty()) {
            System.out.println("No customers founds");
        } else {
            customers.forEach(System.out::println);
        }
    }

    public static void seeAllRooms() {
        Collection<IRoom> rooms = adminResource.getAllRooms();
        if(rooms == null || rooms.isEmpty()) {
            System.out.println("No rooms founds");
        } else {
            rooms.forEach(System.out::println);
        }
    }

    public static void seeAllReservations() {
        adminResource.displayAllReservations();
    }

    private static Integer convertRoomNumber(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception ex) {
            System.out.println("Error: Invalid Input! Please enter a number for room number:");
            return convertRoomNumber(scanner);
        }
    }

    public static void addARoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter room number:");
        String roomNumber = Integer.toString(convertRoomNumber(scanner));

        System.out.println("Enter price per night:");
        double roomPrice = convertRoomPrice(scanner);

        System.out.println("Enter room type: 1 for single bed, 2 for double bed");
        RoomType roomType = convertRoomType(scanner);

        IRoom room = new Room(roomNumber, roomPrice, roomType);

        try {
            adminResource.addRoom(Collections.singletonList(room));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        System.out.println("Would you like to add another room? y/n");
        addAnotherRoom();
    }

    private static void addAnotherRoom() {
        Scanner scanner = new Scanner(System.in);
        String addRoom = scanner.nextLine().trim();

        try {
            if(addRoom.equalsIgnoreCase("y")) {
                addARoom();
            } else if(addRoom.equalsIgnoreCase("n")) {
                System.out.println("Back th Admin Menu");
            } else {
                throw new IllegalArgumentException("Please enter Y (Yes) or N (No)");
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            addAnotherRoom();
        }
    }

    private static double convertRoomPrice(Scanner scanner) {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("Error: invalid room price. please enter a valid double number.");
            return convertRoomPrice(scanner);
        }
    }

    private static RoomType convertRoomType(Scanner scanner) {
        try {
            String type = scanner.nextLine().trim();
            if(type.equals("1")) {
                return RoomType.SINGLE;
            } else if(type.equals("2")) {
                return RoomType.DOUBLE;
            } else {
                throw new IllegalArgumentException("Error: Invalid room type. Please enter 1 or 2.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            return convertRoomType(scanner);
        }
    }

    public static void printAdminMenu() {
        System.out.println("Admin Menu");
        for(String option: options) {
            System.out.println(option);
        }
        System.out.println("Please select a number for the menu option: ");
    }
}
