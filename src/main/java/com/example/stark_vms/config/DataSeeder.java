package com.example.stark_vms.config;

import com.example.stark_vms.entity.Vehicle;
import com.example.stark_vms.enums.FuelType;
import com.example.stark_vms.enums.VehicleStatus;
import com.example.stark_vms.enums.VehicleType;
import com.example.stark_vms.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedVehicles(VehicleRepository repo) {
        return args -> {
            if (repo.count() > 0)
                return;

            repo.saveAll(List.of(
                build("DHA-METRO-GA-11-1001", VehicleType.CAR,       "Toyota",        "Corolla Altis",   2022, "Silver",      FuelType.OCTANE,   5,  "Rahim Uddin",    "+8801711000001", VehicleStatus.AVAILABLE),
                build("DHA-METRO-GA-11-1002", VehicleType.CAR,       "Honda",         "Civic",           2023, "White",       FuelType.PETROL,   5,  "Karim Ahmed",    "+8801711000002", VehicleStatus.IN_USE),
                build("DHA-METRO-GHA-13-2001",VehicleType.SUV,       "Toyota",        "Land Cruiser",    2021, "Black",       FuelType.DIESEL,   7,  "Jamal Hossain",  "+8801711000003", VehicleStatus.AVAILABLE),
                build("DHA-METRO-GHA-13-2002",VehicleType.SUV,       "Mitsubishi",    "Pajero Sport",    2020, "Pearl White", FuelType.DIESEL,   7,  "Kamal Mia",      "+8801711000004", VehicleStatus.MAINTENANCE),
                build("DHA-METRO-CHA-15-3001",VehicleType.MICROBUS,  "Toyota",        "HiAce",           2022, "Silver",      FuelType.CNG,      12, "Rafiqul Islam",  "+8801711000005", VehicleStatus.AVAILABLE),
                build("DHA-METRO-MA-17-4001", VehicleType.VAN,       "Suzuki",        "Every",           2021, "White",       FuelType.LPG,      7,  "Bellal Hossain", "+8801711000007", VehicleStatus.AVAILABLE),
                build("DHA-METRO-TA-19-5001", VehicleType.TRUCK,     "Tata",          "LPT 1615",        2018, "Blue",        FuelType.DIESEL,   3,  "Monirul Islam",  "+8801711000008", VehicleStatus.IN_USE),
                build("DHA-METRO-NA-21-6001", VehicleType.PICKUP,    "Toyota",        "Hilux",           2023, "Grey",        FuelType.DIESEL,   5,  "Anwar Hossain",  "+8801711000011", VehicleStatus.IN_USE),
                build("DHA-METRO-BA-23-7001", VehicleType.BUS,       "Hino",          "1J Plus",         2019, "Green",       FuelType.DIESEL,   40, "Delwar Hossain", "+8801711000012", VehicleStatus.AVAILABLE),
                build("DHA-METRO-LA-25-8001", VehicleType.MOTORCYCLE,"Yamaha",        "FZ-S V3",         2023, "Matte Black", FuelType.OCTANE,   2,  "Tanvir Hasan",   "+8801711000014", VehicleStatus.AVAILABLE),
                build("SYL-METRO-GA-11-9004", VehicleType.CAR,       "BYD",           "Atto 3",          2024, "Blue",        FuelType.ELECTRIC, 5,  "Zahidul Islam",  "+8801711000019", VehicleStatus.AVAILABLE),
                build("RAJ-METRO-CHA-15-9005",VehicleType.MICROBUS,  "Toyota",        "Noah",            2021, "Black",       FuelType.HYBRID,   8,  "Alamin Mia",     "+8801711000020", VehicleStatus.INACTIVE)
            ));
        };
    }

    private Vehicle build(String number, VehicleType type, String brand, String model,
                          int year, String color, FuelType fuel, int seats,
                          String driver, String phone, VehicleStatus status) {
        Vehicle v = new Vehicle();
        v.setVehicleNumber(number);
        v.setVehicleType(type);
        v.setBrand(brand);
        v.setModel(model);
        v.setManufacturingYear(year);
        v.setColor(color);
        v.setFuelType(fuel);
        v.setSeatingCapacity(seats);
        v.setDriverName(driver);
        v.setDriverPhone(phone);
        v.setStatus(status);
        return v;
    }
}
