package com.example.stark_vms.dto;

import com.example.stark_vms.entity.Vehicle;
import com.example.stark_vms.enums.FuelType;
import com.example.stark_vms.enums.VehicleStatus;
import com.example.stark_vms.enums.VehicleType;

import java.time.LocalDateTime;

public record VehicleResponse(
        Long id,
        String vehicleNumber,
        VehicleType vehicleType,
        String brand,
        String model,
        Integer manufacturingYear,
        String color,
        FuelType fuelType,
        Integer seatingCapacity,
        String driverName,
        String driverPhone,
        VehicleStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static VehicleResponse from(Vehicle v) {
        return new VehicleResponse(
                v.getId(),
                v.getVehicleNumber(),
                v.getVehicleType(),
                v.getBrand(),
                v.getModel(),
                v.getManufacturingYear(),
                v.getColor(),
                v.getFuelType(),
                v.getSeatingCapacity(),
                v.getDriverName(),
                v.getDriverPhone(),
                v.getStatus(),
                v.getCreatedAt(),
                v.getUpdatedAt()
        );
    }
}
