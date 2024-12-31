package com.example.car.management.filters;

public class CarFilter {

    private String carMake;
    private Long garageId;
    private Integer fromYear;
    private Integer toYear;

    public CarFilter() {
    }

    public CarFilter(String carMake, Long garageId, Integer fromYear, Integer toYear) {
        this.carMake = carMake;
        this.garageId = garageId;
        this.fromYear = fromYear;
        this.toYear = toYear;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public Long getGarageId() {
        return garageId;
    }

    public void setGarageId(Long garageId) {
        this.garageId = garageId;
    }

    public Integer getFromYear() {
        return fromYear;
    }

    public void setFromYear(Integer fromYear) {
        this.fromYear = fromYear;
    }

    public Integer getToYear() {
        return toYear;
    }

    public void setToYear(Integer toYear) {
        this.toYear = toYear;
    }
}
