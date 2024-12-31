package com.example.car.management.dto;

public class CreateGarageDto {

    private String name;
    private String location;
    private String city;
    private int capacity;
    

    public CreateGarageDto() {
    	
    }

    public CreateGarageDto(String name, String location, String city, int capacity) {
        this.name = name;
        this.location = location;
        this.city = city;
        this.capacity = capacity;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
