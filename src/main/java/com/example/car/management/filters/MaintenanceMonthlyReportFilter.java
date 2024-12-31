package com.example.car.management.filters;

import java.time.LocalDate;

public class MaintenanceMonthlyReportFilter {

    private Long garageId;
    private LocalDate startMonth;
    private LocalDate endMonth;

    public MaintenanceMonthlyReportFilter() {
    }

    public MaintenanceMonthlyReportFilter(Long garageId, LocalDate startMonth, LocalDate endMonth) {
        this.garageId = garageId;
        this.startMonth = startMonth;
        this.endMonth = endMonth;
    }
    
    public Long getGarageId() {
        return garageId;
    }

    public void setGarageId(Long garageId) {
        this.garageId = garageId;
    }

    public LocalDate getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(LocalDate startMonth) {
        this.startMonth = startMonth;
    }

    public LocalDate getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(LocalDate endMonth) {
        this.endMonth = endMonth;
    }
}
