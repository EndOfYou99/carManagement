package com.example.car.management.controllers;

import com.example.car.management.dto.CarDto;
import com.example.car.management.dto.CreateCarDto;
import com.example.car.management.dto.DtoFactory;
import com.example.car.management.dto.UpdateCarDto;
import com.example.car.management.filters.CarFilter;
import com.example.car.management.models.Car;
import com.example.car.management.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;


    @GetMapping
    public ResponseEntity<List<CarDto>> getCars(@RequestParam(required = false) String carMake,
                                                @RequestParam(required = false) Long garageId,
                                                @RequestParam(required = false) Integer fromYear,
                                                @RequestParam(required = false) Integer toYear) {
        CarFilter filter = new CarFilter(carMake, garageId, fromYear, toYear);
        try {
        List<CarDto> cars = carService.getCars(filter).stream().map(DtoFactory::asDto).collect(Collectors.toList());
        return ResponseEntity.ok(cars);
        } catch(Exception ex) {
        	return ResponseEntity.badRequest().build();
        }
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCar(@PathVariable Long id) {
    	try {
        Car car = carService.getCarById(id);
        return ResponseEntity.ok(DtoFactory.asDto(car));
        }catch (Exception ex) {
            return  ResponseEntity.notFound().build();
        	
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCar(@PathVariable Long id, @RequestBody UpdateCarDto carDto) {
    	try {
        carService.updateCar(id, carDto);
    	} catch(Exception ex) {
    		return ResponseEntity.badRequest().build();
    	}
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<CarDto> createCar(@RequestBody CreateCarDto carDto) {
    	try {
        Car car = carService.createCar(carDto);
        return ResponseEntity.created(null).body(DtoFactory.asDto(car));
        }catch (Exception ex) {
        	return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
    try {
        carService.deleteCar(id);
	} catch(Exception ex) {
		return ResponseEntity.badRequest().build();
	}
        return ResponseEntity.ok().build();
    }
}
