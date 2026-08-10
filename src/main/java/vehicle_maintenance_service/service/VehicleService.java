package vehicle_maintenance_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vehicle_maintenance_service.entity.Vehicle;
import vehicle_maintenance_service.exception.DuplicateResourceException;
import vehicle_maintenance_service.exception.ResourceNotFoundException;
import vehicle_maintenance_service.repository.VehicleRepository;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle createVehicle(Vehicle vehicle) {

        if (vehicleRepository
                .findByRegistrationNumber(vehicle.getRegistrationNumber())
                .isPresent()) {

            throw new DuplicateResourceException(
                    "Vehicle with registration number "
                    + vehicle.getRegistrationNumber()
                    + " already exists"
            );
        }

        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(Long id) {

        return vehicleRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle with ID " + id + " not found"
                        )
                );
    }

    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {

        Vehicle existingVehicle = getVehicleById(id);

        existingVehicle.setRegistrationNumber(
                updatedVehicle.getRegistrationNumber()
        );

        existingVehicle.setMake(
                updatedVehicle.getMake()
        );

        existingVehicle.setModel(
                updatedVehicle.getModel()
        );

        existingVehicle.setYear(
                updatedVehicle.getYear()
        );

        existingVehicle.setCurrentMileage(
                updatedVehicle.getCurrentMileage()
        );

        existingVehicle.setOwnerName(
                updatedVehicle.getOwnerName()
        );

        return vehicleRepository.save(existingVehicle);
    }

    public void deleteVehicle(Long id) {

        Vehicle vehicle = getVehicleById(id);

        vehicleRepository.delete(vehicle);
    }
}