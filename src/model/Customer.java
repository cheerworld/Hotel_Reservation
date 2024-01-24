package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) {
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);

        if(pattern.matcher(email).matches()) {
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
        } else {
            throw new IllegalArgumentException("Error: Invalid Input");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + ", Email: " + email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Customer customer = (Customer) obj;
        return email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
