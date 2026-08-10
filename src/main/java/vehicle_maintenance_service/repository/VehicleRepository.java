package vehicle_maintenance_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import vehicle_maintenance_service.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);
}