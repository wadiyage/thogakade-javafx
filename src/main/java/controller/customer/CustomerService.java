package controller.customer;

import model.Customer;

import java.util.List;

public interface CustomerService {
    boolean addCustomer(Customer customer);
    List<Customer> getAll();
    Customer searchCustomerById(String id);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer(String id);
}
