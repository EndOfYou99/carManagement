package com.example.car.management.dto;

import java.time.LocalDate;

public class GarageDailyAvailabilityReportDto {
	
	LocalDate date;
	int requests;
	int availableCapacity;
	
	
	
	public GarageDailyAvailabilityReportDto(LocalDate date, int requests, int availableCapacity) {
		super();
		this.date = date;
		this.requests = requests;
		this.availableCapacity = availableCapacity;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getRequests() {
		return requests;
	}
	public void setRequests(int requests) {
		this.requests = requests;
	}
	public int getAvailableCapacity() {
		return availableCapacity;
	}
	public void setAvailableCapacity(int availableCapacity) {
		this.availableCapacity = availableCapacity;
	}
	
	

}
