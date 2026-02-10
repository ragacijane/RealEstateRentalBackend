package com.project.RealEstateRental.dtos;

public class CustomerDTO {
    private Integer idCustomer;
    private String firstName;
    private String secondName;
    private String email;
    private String phoneNumber;
    private String clientType;
    private String demand;
    private String offers;
    private String showed;
    private String pros;
    private String cons;
    private String contract;
    private String description;
    private boolean isActive;

    public CustomerDTO() {
    }

    public CustomerDTO(
            Integer idCustomer,
            String firstName,
            String secondName,
            String email,
            String phoneNumber,
            String clientType,
            String demand,
            String offers,
            String showed,
            String pros,
            String cons,
            String contract,
            String description,
            boolean isActive
    ) {
        this.idCustomer = idCustomer;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.clientType = clientType;
        this.demand = demand;
        this.offers = offers;
        this.showed = showed;
        this.pros = pros;
        this.cons = cons;
        this.contract = contract;
        this.description = description;
        this.isActive = isActive;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public String getShowed() {
        return showed;
    }

    public void setShowed(String showed) {
        this.showed = showed;
    }

    public String getPros() {
        return pros;
    }

    public void setPros(String pros) {
        this.pros = pros;
    }

    public String getCons() {
        return cons;
    }

    public void setCons(String cons) {
        this.cons = cons;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
