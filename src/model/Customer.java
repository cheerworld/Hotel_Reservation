package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) {
        final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9-]+\\.(com)$";
        final Pattern pattern = Pattern.compile(emailRegex);

        if(pattern.matcher(email).matches()) {
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;
        } else {
            throw new IllegalArgumentException("Error: Invalid Input");
        }
    }

    public final String getFirstName() {
        return firstName;
    }

    public final void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public final String getLastName() {
        return lastName;
    }

    public final void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail() {
        this.email = email;
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
