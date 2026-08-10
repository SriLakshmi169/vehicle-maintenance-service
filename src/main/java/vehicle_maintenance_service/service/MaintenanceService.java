package vehicle_maintenance_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vehicle_maintenance_service.entity.MaintenanceRecord;
import vehicle_maintenance_service.entity.Vehicle;
import vehicle_maintenance_service.exception.ResourceNotFoundException;
import vehicle_maintenance_service.repository.MaintenanceRecordRepository;
import vehicle_maintenance_service.repository.VehicleRepository;

@Service
public class MaintenanceService {

    private final MaintenanceRecordRepository maintenanceRepository;
    private final VehicleRepository vehicleRepository;

    public MaintenanceService(
            MaintenanceRecordRepository maintenanceRepository,
            VehicleRepository vehicleRepository) {

        this.maintenanceRepository = maintenanceRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public MaintenanceRecord addMaintenance(
            Long vehicleId,
            MaintenanceRecord maintenanceRecord) {

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle with ID " + vehicleId + " not found"
                        )
                );

        maintenanceRecord.setVehicle(vehicle);

        return maintenanceRepository.save(maintenanceRecord);
    }

    public List<MaintenanceRecord> getMaintenanceHistory(
            Long vehicleId) {

        if (!vehicleRepository.existsById(vehicleId)) {

            throw new ResourceNotFoundException(
                    "Vehicle with ID " + vehicleId + " not found"
            );
        }

        return maintenanceRepository.findByVehicleId(vehicleId);
    }

    public List<MaintenanceRecord> getDueMaintenance(
            Long vehicleId) {

        vehicleRepository.findById(vehicleId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Vehicle with ID " + vehicleId + " not found"
                        )
                );

        return maintenanceRepository
                .findByVehicleIdAndStatus(vehicleId, "DUE");
    }
}