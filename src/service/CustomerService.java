package service;

import model.Customer;
import model.IRoom;

import java.util.*;

public class CustomerService {

    private static final CustomerService reference = new CustomerService();
    private final Set<Customer> customers;
    private CustomerService() {
        this.customers = new HashSet<Customer>();
    }

    public final void addCustomer(String email, String firstName, String lastName) {
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                throw new IllegalArgumentException("The email address you entered already exists in the customer list, please enter a different email address.");
            }
        }
        customers.add(new Customer(firstName, lastName, email));
    }


    public final Customer getCustomer(String customerEmail) {
        for (Customer customer: customers) {
            if (customer.getEmail().equals(customerEmail)) {
                return customer;
            }
        }
        return null;
    }
    public final Collection<Customer> getAllCustomers() {
        return customers;
    }
    public static CustomerService getInstance() {
        return reference;
    }
}
