package vehicle_maintenance_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vehicle_maintenance_service.entity.MaintenanceRecord;
import vehicle_maintenance_service.entity.Vehicle;

public interface MaintenanceRecordRepository
        extends JpaRepository<MaintenanceRecord, Long> {

    List<MaintenanceRecord> findByVehicle(Vehicle vehicle);

    List<MaintenanceRecord> findByVehicleId(Long vehicleId);

    List<MaintenanceRecord> findByVehicleIdAndStatus(
            Long vehicleId,
            String status
    );
}