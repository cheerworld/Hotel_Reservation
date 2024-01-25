import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainMenu {

    private static final String[] options = new String[] {
        "1. Find and reserve a room",
        "2. See my reservations",
        "3. Create an account",
        "4. Admin",
        "5. Exit"
    };
    private static HotelResource hotelResource;
    private static final String DATE_FORMAT = "MM/dd/yyyy";

    public static void mainMenu() {

        hotelResource = HotelResource.getInstance();

        Scanner scanner = new Scanner(System.in);
        int option = 1;

        printMenu();

        try {
            option=scanner.nextInt();
            System.out.println(option);

            switch(option) {
                case 1:
                    findAndReserveRoom();
                    break;
                case 2:
                    seeMyReservations();
                    break;
                case 3:
                    createAnAccount();
                    break;
                case 4:

                    break;
                case 5:
                    System.out.println("Exit");
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

    private static void findAndReserveRoom() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter CheckIn Date mm/dd/yyyy example 01/01/2024");
        String checkInDate = scanner.nextLine().trim();
        Date checkIn = parseDate(checkInDate);

        System.out.println("Enter CheckOut Date mm/dd/yyyy example 01/21/2024");
        String checkOutDate = scanner.nextLine().trim();
        Date checkOut = parseDate(checkOutDate);

        if(checkIn != null && checkOut != null) {
            Collection<IRoom> availableRooms = hotelResource.findARoom(checkIn, checkOut);

            if(availableRooms.isEmpty()) {
                Collection<IRoom> alternativeRooms = hotelResource.findAlternativeRooms(checkIn, checkOut);
                if (alternativeRooms.isEmpty()) {
                    System.out.println("No available rooms found.");
                } else {
                    Date alternativeCheckIn = hotelResource.add7Days(checkIn);
                    Date alternativeCheckOut = hotelResource.add7Days(checkOut);
                    System.out.println("We've only found rooms on alternative dates: ");
                    System.out.println("Check-In Date: " + new SimpleDateFormat(DATE_FORMAT).format(alternativeCheckIn));
                    System.out.println("Check-Out Date: " + new SimpleDateFormat(DATE_FORMAT).format(alternativeCheckOut));

                    alternativeRooms.forEach(System.out::println);
                    //call function to reserve a room
                    reserveRoom(scanner, alternativeRooms, alternativeCheckIn, alternativeCheckOut);
                }
            } else {
                availableRooms.forEach(System.out::println);
                //call function to reserve a room
                reserveRoom(scanner, availableRooms, checkIn, checkOut);
            }

        }
    }

    private static void reserveRoom(Scanner scanner, Collection<IRoom> rooms, Date checkInDate, Date checkOutDate) {
        System.out.println("Would you like to book a room? y/n");
        String bookRoom = scanner.nextLine().trim();

        if(bookRoom.equalsIgnoreCase("y")) {
            System.out.println("Do you have an account with us? y/n");
            String haveAccount = scanner.nextLine().trim();
            if(haveAccount.equalsIgnoreCase("y")) {
                System.out.println("Enter Email Format: name@domain.com");
                String email = scanner.nextLine().trim();
                Customer customer = hotelResource.getCustomer(email);
                if(customer != null) {
                    System.out.println("What room number would you like to reserve?");
                    String roomNumber = scanner.nextLine().trim();
                    boolean found = false;
                    for(IRoom room: rooms) {
                        if(room.getRoomNumber().equals(roomNumber)) {
                            //make a reservation and print reservation
                            IRoom findRoom = hotelResource.getRoom(roomNumber);
                            Reservation bookReservation = hotelResource.bookARoom(email, findRoom, checkInDate, checkOutDate);

                            System.out.println("Reservation: ");
                            System.out.println(bookReservation);
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        System.out.println("Error: can't find available rooms for this room number");
                        printMenu();
                    }
                } else {
                    System.out.println("Error: can't find customer that associate with the provided email");
                    printMenu();
                }
            } else if(haveAccount.equalsIgnoreCase("n")) {
                System.out.println("Please create an account.");
                printMenu();
            } else {
                System.out.println("Error: Invalid Input");
                reserveRoom(scanner, rooms, checkInDate, checkOutDate);
            }
        } else if(bookRoom.equalsIgnoreCase("n")) {
            printMenu();
        } else {
            System.out.println("Error: Invalid Input");
            reserveRoom(scanner, rooms, checkInDate, checkOutDate);
        }
    }

    private static Date parseDate(String date) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(date);
        } catch (ParseException ex) {
            System.out.println("Error: Invalid date format. Please enter the date in mm/dd/yyyy format.");
            findAndReserveRoom();
        }
        return null;
    }

    private static void seeMyReservations() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the email that you registered with us to view all your reservations");
        String email = scanner.nextLine().trim();
        Collection<Reservation> reservations = hotelResource.getCustomersReservations(email);

        if(reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations found for this account.");
            printMenu();
        } else {
            reservations.forEach(reservation -> System.out.println("\n" + reservation));
        }
    }

    private static void createAnAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Email Format: name@domain.com");
        String email = scanner.nextLine().trim();

        System.out.println("First Name: ");
        String firstName = scanner.nextLine().trim();

        System.out.println("Last Name: ");
        String lastName = scanner.nextLine().trim();

        try {
            hotelResource.createACustomer(email, firstName, lastName);
            System.out.println("Account created successfully!");
            printMenu();
        } catch(Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            createAnAccount();
        }
    }

    public static void printMenu() {
        System.out.println("Welcome to the Hotel Reservation Application");
        for(String option: options) {
            System.out.println(option);
        }
        System.out.println("Please select a number for the menu option: ");
    }
}
