package com.project.RealEstateRental.dtos;

import com.project.RealEstateRental.models.Customers;

import java.util.List;

public class GetCustomerResponse {

    private List<Customers> customers;
    private long totalCount;

    public GetCustomerResponse() {
    }

    public GetCustomerResponse(List<Customers> customers, long totalCount) {
        this.customers = customers;
        this.totalCount = totalCount;
    }

    public List<Customers> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customers> customers) {
        this.customers = customers;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
