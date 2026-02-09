package com.project.RealEstateRental.services;
import com.project.RealEstateRental.dtos.CustomerDTO;
import com.project.RealEstateRental.models.Customers;
import com.project.RealEstateRental.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomersService {

    private final CustomersRepository customersRepository;

    @Autowired
    public CustomersService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public CustomerDTO getCustomerById(Integer idCustomer) {

        if (idCustomer == null) {
            throw new IllegalArgumentException("Customer id must not be null");
        }

        Customers customer = customersRepository.findById(idCustomer)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return new CustomerDTO(
                (int) customer.getIdCustomer(),
                customer.getFirstName(),
                customer.getSecondName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getClientType(),
                customer.getDemand(),
                customer.getOffers(),
                customer.getShowed(),
                customer.getPros(),
                customer.getCons(),
                customer.getContract(),
                customer.getDescription(),
                customer.isActive()
        );
    }



    public long createCustomer(CustomerDTO dto) {
        if (dto.getIdCustomer() != null) {
            throw new IllegalArgumentException("Customer id must be null when creating");
        }

        Customers customer = new Customers(dto);
        return customersRepository.save(customer).getIdCustomer();
    }

    public long updateCustomer(CustomerDTO dto) {
        if (dto.getIdCustomer() == null) {
            throw new IllegalArgumentException("Customer id must not be null for update");
        }

        Customers existing = customersRepository.findById(dto.getIdCustomer())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existing.updateFromDto(dto);

        return customersRepository.save(existing).getIdCustomer();
    }

    public List<Customers> getFilteredCustomers(
            Integer page,
            Integer size,
            String sort,
            boolean asc,
            Integer idCustomer,
            String firstName,
            String secondName,
            String email,
            String phoneNumber,
            String clientType
    ) {
        Pageable pageable = asc
                ? PageRequest.of(page, size, Sort.by("id_customer").ascending())
                : PageRequest.of(page, size, Sort.by("id_customer").descending());

        return customersRepository.findByFilter(
                idCustomer,
                firstName,
                secondName,
                email,
                phoneNumber,
                clientType,
                pageable
        );
    }
}
