package vehicle_maintenance_service.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "maintenance_records")
public class MaintenanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serviceType;

    @Column(nullable = false)
    private LocalDate serviceDate;

    @Column(nullable = false)
    private double mileage;

    @Column(nullable = false)
    private double cost;

    private String description;

    private double nextServiceMileage;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    public MaintenanceRecord() {
    }

    public MaintenanceRecord(String serviceType,
                             LocalDate serviceDate,
                             double mileage,
                             double cost,
                             String description,
                             double nextServiceMileage,
                             String status,
                             Vehicle vehicle) {
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.mileage = mileage;
        this.cost = cost;
        this.description = description;
        this.nextServiceMileage = nextServiceMileage;
        this.status = status;
        this.vehicle = vehicle;
    }

    public Long getId() {
        return id;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getNextServiceMileage() {
        return nextServiceMileage;
    }

    public void setNextServiceMileage(double nextServiceMileage) {
        this.nextServiceMileage = nextServiceMileage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}