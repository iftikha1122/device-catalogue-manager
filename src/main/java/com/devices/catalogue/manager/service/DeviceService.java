package com.devices.catalogue.manager.service;



import com.devices.catalogue.manager.dto.CreateRequestDto;
import com.devices.catalogue.manager.dto.DeviceResponseDTO;
import com.devices.catalogue.manager.dto.PartialUpdateRequestDto;
import com.devices.catalogue.manager.exceptions.DeviceNotFoundException;
import com.devices.catalogue.manager.domain.Device;
import com.devices.catalogue.manager.exceptions.InvalidPartialUpdateException;
import com.devices.catalogue.manager.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceResponseDTO addDevice(CreateRequestDto createRequestDto) {
        log.info("Adding a new device with name: {} and brand: {}",
                createRequestDto.getName(), createRequestDto.getBrand());
        Device device = Device.fromDto(createRequestDto);
        Device savedDevice = deviceRepository.save(device);
        log.info("Device added successfully with id: {}",
                savedDevice.getId());
        return DeviceResponseDTO.fromEntity(savedDevice);
    }

    public DeviceResponseDTO getDeviceById(Long id) {
        log.info("Fetching device with id: {}", id);
        return deviceRepository.findById(id)
                .map(DeviceResponseDTO::fromEntity).orElseThrow(()->new DeviceNotFoundException(id));
    }

    public List<DeviceResponseDTO> getAllDevices() {
        log.info("Fetching all devices");
        return deviceRepository.findAll().stream()
                .map(DeviceResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public DeviceResponseDTO updateDevice(Long id, CreateRequestDto createRequestDto) {
        log.info("Updating device with id: {}", id);
        Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id));
        device.setName(createRequestDto.getName());
        device.setBrand(createRequestDto.getBrand());
        Device updatedDevice = deviceRepository.save(device);
        log.info("Device with id: {} updated successfully", id);
        return DeviceResponseDTO.fromEntity(updatedDevice);
    }


    public DeviceResponseDTO partialUpdateDevice(Long id, PartialUpdateRequestDto dto) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new DeviceNotFoundException(id));

        boolean isUpdated = false;

            if (dto.getName() != null && !dto.getName().isEmpty()) {
                device.setName(dto.getName());
                isUpdated = true;
            }
            if (dto.getBrand() != null && !dto.getBrand().isEmpty()) {
                device.setBrand(dto.getBrand());
                isUpdated = true;
            }

            if (!isUpdated) {
                throw new InvalidPartialUpdateException();
            }

            device = deviceRepository.save(device);


        return DeviceResponseDTO.fromEntity(device);
    }

    public void deleteDevice(Long id) {
        log.info("Deleting device with id: {}", id);
        if (deviceRepository.findById(id).isEmpty()) {
            throw new DeviceNotFoundException(id);
        }
        log.info("Device with id: {} deleted successfully", id);
        deviceRepository.deleteById(id);
    }

    public List<DeviceResponseDTO> searchDevicesByBrand(String brand) {
        log.info("Searching devices with brand: {}", brand);
        return deviceRepository.findByBrand(brand).stream()
                .map(DeviceResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
