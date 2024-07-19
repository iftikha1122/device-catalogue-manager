package com.devices.catalogue.manager.api;



import com.devices.catalogue.manager.dto.CreateRequestDto;
import com.devices.catalogue.manager.dto.DeviceResponseDTO;
import com.devices.catalogue.manager.dto.PartialUpdateRequestDto;
import com.devices.catalogue.manager.service.DeviceService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@RestController
@RequestMapping("/devices")
@Api(tags = "device catalogue manager service")
@Validated
@RequiredArgsConstructor
public class DeviceApi {

    @Autowired
    private final DeviceService deviceService;

    @Operation(summary = "Add a new device")
    @ApiResponse(responseCode = "200", description = "Device successfully added")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DeviceResponseDTO> addDevice(@Valid @RequestBody CreateRequestDto createRequestDto) {
        DeviceResponseDTO savedDevice = deviceService.addDevice(createRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDevice);
    }

    @Operation(summary = "Get a device by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description= "Found the device"),
            @ApiResponse(responseCode = "404", description= "Device not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DeviceResponseDTO> getDeviceById(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    @Operation(summary = "Get all devices")
    @ApiResponse(responseCode = "200", description = "List of all devices")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<DeviceResponseDTO>> getAllDevices() {
        List<DeviceResponseDTO> devices = deviceService.getAllDevices();
        return ResponseEntity.ok(devices);
    }

    @Operation(summary = "Update a device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device successfully updated"),
            @ApiResponse(responseCode = "404", description = "Device not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> updateDevice(@PathVariable Long id, @Valid @RequestBody CreateRequestDto createRequestDto) {
        DeviceResponseDTO updatedDevice = deviceService.updateDevice(id, createRequestDto);
        return ResponseEntity.ok(updatedDevice);
    }

    @Operation(summary = "Partially update a device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device successfully updated"),
            @ApiResponse(responseCode = "404", description = "Device not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> partialUpdateDevice(@PathVariable Long id, @Valid @RequestBody PartialUpdateRequestDto partialUpdateRequestDto ) {
        DeviceResponseDTO updatedDevice = deviceService.partialUpdateDevice(id, partialUpdateRequestDto);
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search devices by brand")
    @ApiResponse(responseCode = "200", description = "List of devices matching the brand")
    @GetMapping("/search")
    public ResponseEntity<List<DeviceResponseDTO>> searchDevicesByBrand(
            @Parameter(description = "Brand of the devices to be searched") @RequestParam String brand) {
        List<DeviceResponseDTO> devices = deviceService.searchDevicesByBrand(brand);
        return ResponseEntity.ok(devices);
    }
}
