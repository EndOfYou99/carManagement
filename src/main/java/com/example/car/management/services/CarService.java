package com.example.car.management.services;

import com.example.car.management.dto.CarDto;
import com.example.car.management.dto.CreateCarDto;
import com.example.car.management.dto.UpdateCarDto;
import com.example.car.management.filters.CarFilter;
import com.example.car.management.models.Car;
import com.example.car.management.models.Garage;
import com.example.car.management.repositories.CarRepository;
import com.example.car.management.repositories.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private GarageRepository garageRepository;

    @Transactional(readOnly = true)
    public List<Car> getCars(CarFilter filter) {
        List<Car> cars = carRepository.findAll();

        if (filter.getCarMake() != null && !filter.getCarMake().isEmpty()) {
            cars = cars.stream().filter(c -> c.getMake().toLowerCase().contains(filter.getCarMake().toLowerCase())).collect(Collectors.toList());
        }

        if (filter.getGarageId() != null) {
            Long garageId = filter.getGarageId();
            cars = cars.stream().filter(c -> c.getGarages().stream().anyMatch(g -> g.getId().equals(garageId))).collect(Collectors.toList());
        }

        if (filter.getFromYear() != null) {
            cars = cars.stream().filter(c -> c.getProductionYear() >= filter.getFromYear()).collect(Collectors.toList());
        }

        if (filter.getToYear() != null) {
            cars = cars.stream().filter(c -> c.getProductionYear() <= filter.getToYear()).collect(Collectors.toList());
        }

        return cars;
    }

    @Transactional(readOnly = true)
    public Car getCarById(Long id) {
        return carRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Car not found"));
    }

    @Transactional
    public void updateCar(Long id, UpdateCarDto carDto) {
        Car car = getCarById(id);
        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setProductionYear(carDto.getProductionYear());
        car.setLicensePlate(carDto.getLicensePlate());

        List<Garage> garages = carDto.getGarageIds().stream()
                .map(garageId -> garageRepository.findById(garageId).orElseThrow(() -> new IllegalArgumentException("Garage not found")))
                .collect(Collectors.toList());
        car.setGarages(garages);

        carRepository.save(car);
    }

    @Transactional
    public Car createCar(CreateCarDto carDto) {
        List<Garage> garages = carDto.getGarageIds().stream()
                .map(garageId -> garageRepository.findById(garageId).orElseThrow(() -> new IllegalArgumentException("Garage not found")))
                .collect(Collectors.toList());

        Car car = new Car();
        car.setMake(carDto.getMake());
        car.setModel(carDto.getModel());
        car.setProductionYear(carDto.getProductionYear());
        car.setLicensePlate(carDto.getLicensePlate());
        car.setGarages(garages);

        return carRepository.save(car);
    }

    @Transactional
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new IllegalArgumentException("Car not found");
        }
        carRepository.deleteById(id);
    }
}
