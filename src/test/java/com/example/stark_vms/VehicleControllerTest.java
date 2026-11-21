package com.example.stark_vms;

import com.example.stark_vms.dto.VehicleResponse;
import com.example.stark_vms.entity.Vehicle;
import com.example.stark_vms.enums.FuelType;
import com.example.stark_vms.enums.VehicleStatus;
import com.example.stark_vms.enums.VehicleType;
import com.example.stark_vms.exception.DuplicateResourceException;
import com.example.stark_vms.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @Autowired
    private ObjectMapper objectMapper;

    // ── Helper ────────────────────────────────────────────────────────────────

    private VehicleResponse sample() {
        return new VehicleResponse(
                1L, "DHA-TEST-GA-11-0001", VehicleType.CAR,
                "Toyota", "Corolla", 2023, "Silver",
                FuelType.HYBRID, 5, "Test Driver", "+8801700000000",
                VehicleStatus.AVAILABLE, LocalDateTime.now(), LocalDateTime.now());
    }

    // ── GET /api/vehicles ─────────────────────────────────────────────────────

    @Test
    void getVehicles_returns200WithPage() throws Exception {
        var page = new PageImpl<>(List.of(sample()));
        when(vehicleService.getVehicles(0, 10)).thenReturn(page);

        mockMvc.perform(get("/api/vehicles?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].vehicleNumber").value("DHA-TEST-GA-11-0001"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    // ── GET /api/vehicles/{id} ────────────────────────────────────────────────

    @Test
    void getVehicleById_exists_returns200() throws Exception {
        when(vehicleService.getVehicleById(1L)).thenReturn(sample());

        mockMvc.perform(get("/api/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    // ── POST /api/vehicles ────────────────────────────────────────────────────

    @Test
    void createVehicle_valid_returns201() throws Exception {
        when(vehicleService.createVehicle(any(Vehicle.class))).thenReturn(sample());

        String payload = """
                {
                  "vehicleNumber": "DHA-TEST-GA-11-0001",
                  "vehicleType":   "CAR",
                  "brand":         "Toyota",
                  "model":         "Corolla",
                  "fuelType":      "HYBRID",
                  "status":        "AVAILABLE"
                }
                """;

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vehicleNumber").value("DHA-TEST-GA-11-0001"));
    }

    @Test
    void createVehicle_duplicateNumber_returns409() throws Exception {
        when(vehicleService.createVehicle(any(Vehicle.class)))
                .thenThrow(new DuplicateResourceException("Vehicle with number 'DHA-TEST-GA-11-0001' already exists"));

        String payload = """
                {
                  "vehicleNumber": "DHA-TEST-GA-11-0001",
                  "vehicleType":   "CAR",
                  "brand":         "Toyota",
                  "model":         "Corolla",
                  "fuelType":      "HYBRID"
                }
                """;

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("Vehicle with number 'DHA-TEST-GA-11-0001' already exists"));
    }

    @Test
    void createVehicle_missingRequiredFields_returns400() throws Exception {
        String payload = """
                {
                  "vehicleNumber": "",
                  "brand": "",
                  "model": ""
                }
                """;

        mockMvc.perform(post("/api/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.vehicleNumber").value("Vehicle number is required"));
    }

    // ── PUT /api/vehicles/{id} ────────────────────────────────────────────────

    @Test
    void updateVehicle_valid_returns200() throws Exception {
        VehicleResponse updated = new VehicleResponse(
                1L, "DHA-TEST-GA-11-0001", VehicleType.CAR,
                "Toyota", "Corolla", 2023, "Black",
                FuelType.HYBRID, 5, "Test Driver", "+8801700000000",
                VehicleStatus.IN_USE, LocalDateTime.now(), LocalDateTime.now());

        when(vehicleService.updateVehicle(eq(1L), any(Vehicle.class))).thenReturn(updated);

        String payload = """
                {
                  "vehicleNumber": "DHA-TEST-GA-11-0001",
                  "vehicleType":   "CAR",
                  "brand":         "Toyota",
                  "model":         "Corolla",
                  "fuelType":      "HYBRID",
                  "color":         "Black",
                  "status":        "IN_USE"
                }
                """;

        mockMvc.perform(put("/api/vehicles/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.color").value("Black"))
                .andExpect(jsonPath("$.status").value("IN_USE"));
    }

    // ── DELETE /api/vehicles/{id} ─────────────────────────────────────────────

    @Test
    void deleteVehicle_exists_returns204() throws Exception {
        doNothing().when(vehicleService).deleteVehicle(1L);

        mockMvc.perform(delete("/api/vehicles/1"))
                .andExpect(status().isNoContent());
    }
}
