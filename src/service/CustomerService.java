package service;

import model.Customer;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class CustomerService {

    private static CustomerService INSTANCE;
    private Map<String, Customer> customerMap = new Hashtable<>();
    Customer customer;

    private CustomerService() {}

    public static CustomerService getINSTANCE() {
        if (INSTANCE == null) {
            synchronized (CustomerService.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CustomerService();
                }
            }
        }
        return INSTANCE;
    }


    public void addCustomer (String firstName, String lastName, String email) {
        try {
            customer = new Customer(firstName, lastName, email);
            customerMap.put(customer.getEmail(), customer);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    public Customer getCustomer (String email) {
        try {
            customer = customerMap.get(email);
        } catch (NullPointerException e) {
            System.out.println("There is no registered customer with email: " + email);
        }
        return customer;
    }

    public Collection<Customer> getAllCustomers () {
        return customerMap.values();
    }


}
