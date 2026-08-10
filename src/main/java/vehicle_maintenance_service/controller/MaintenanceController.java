package vehicle_maintenance_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vehicle_maintenance_service.entity.MaintenanceRecord;
import vehicle_maintenance_service.service.MaintenanceService;

@RestController
@RequestMapping("/api/vehicles/{vehicleId}/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping
    public ResponseEntity<MaintenanceRecord> addMaintenance(
            @PathVariable Long vehicleId,
            @RequestBody MaintenanceRecord maintenanceRecord) {

        MaintenanceRecord savedRecord =
                maintenanceService.addMaintenance(
                        vehicleId,
                        maintenanceRecord
                );

        return new ResponseEntity<>(
                savedRecord,
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<List<MaintenanceRecord>> getMaintenanceHistory(
            @PathVariable Long vehicleId) {

        return ResponseEntity.ok(
                maintenanceService.getMaintenanceHistory(vehicleId)
        );
    }

    @GetMapping("/due")
    public ResponseEntity<List<MaintenanceRecord>> getDueMaintenance(
            @PathVariable Long vehicleId) {

        return ResponseEntity.ok(
                maintenanceService.getDueMaintenance(vehicleId)
        );
    }
}