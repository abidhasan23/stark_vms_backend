package com.example.stark_vms.service;

import com.example.stark_vms.dto.VehicleResponse;
import com.example.stark_vms.entity.Vehicle;
import com.example.stark_vms.enums.VehicleStatus;
import com.example.stark_vms.exception.DuplicateResourceException;
import com.example.stark_vms.exception.ResourceNotFoundException;
import com.example.stark_vms.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional(readOnly = true)
    public Page<VehicleResponse> getVehicles(int page, int size) {
        if (page < 0) throw new IllegalArgumentException("Page index must not be less than zero");
        if (size < 1) throw new IllegalArgumentException("Page size must not be less than one");
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return vehicleRepository.findAll(pageable).map(VehicleResponse::from);
    }

    @Transactional(readOnly = true)
    public VehicleResponse getVehicleById(Long id) {
        return VehicleResponse.from(findById(id));
    }

    @Transactional
    public VehicleResponse createVehicle(Vehicle vehicle) {
        String number = vehicle.getVehicleNumber().trim();
        if (vehicleRepository.existsByVehicleNumberIgnoreCase(number)) {
            throw new DuplicateResourceException(
                    "Vehicle with number '" + number + "' already exists");
        }
        vehicle.setId(null);
        vehicle.setVehicleNumber(number);
        if (vehicle.getStatus() == null) vehicle.setStatus(VehicleStatus.AVAILABLE);
        return VehicleResponse.from(vehicleRepository.save(vehicle));
    }

    @Transactional
    public VehicleResponse updateVehicle(Long id, Vehicle payload) {
        Vehicle existing = findById(id);
        String number = payload.getVehicleNumber().trim();
        if (vehicleRepository.existsByVehicleNumberIgnoreCaseAndIdNot(number, id)) {
            throw new DuplicateResourceException(
                    "Vehicle with number '" + number + "' already exists");
        }
        existing.setVehicleNumber(number);
        existing.setVehicleType(payload.getVehicleType());
        existing.setBrand(payload.getBrand());
        existing.setModel(payload.getModel());
        existing.setManufacturingYear(payload.getManufacturingYear());
        existing.setColor(payload.getColor());
        existing.setFuelType(payload.getFuelType());
        existing.setSeatingCapacity(payload.getSeatingCapacity());
        existing.setDriverName(payload.getDriverName());
        existing.setDriverPhone(payload.getDriverPhone());
        existing.setStatus(payload.getStatus() != null ? payload.getStatus() : existing.getStatus());
        return VehicleResponse.from(vehicleRepository.save(existing));
    }

    @Transactional
    public void deleteVehicle(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found with id: " + id);
        }
        vehicleRepository.deleteById(id);
    }

    private Vehicle findById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }
}
