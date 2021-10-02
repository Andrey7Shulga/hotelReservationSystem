package model;

import service.CustomerService;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {

    private String firstName, lastName, email;

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
            if (!checkEmail(email)) {
                throw new IllegalArgumentException("Please, type a correct email that matches the format - name@domain.com");
            }
        this.email = email;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private boolean checkEmail (String email) {
        String emailRegex = "^(.+)@(.+).(.+)";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }


    @Override
    public String toString() {
        return "Customer:" +
                " firstName = '" + firstName + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", email = '" + email + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return firstName.equals(customer.firstName) && lastName.equals(customer.lastName) && email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        int result = 13;

        if (firstName != null) {result = result * 7 + firstName.hashCode();}
        if (lastName != null) {result = result * 7 + lastName.hashCode();}
        if (email != null) {result = result * 7 + email.hashCode();}

        return result;
    }
}
