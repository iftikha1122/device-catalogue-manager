package com.devices.catalogue.manager.service;



import com.devices.catalogue.manager.dto.CreateRequestDto;
import com.devices.catalogue.manager.dto.DeviceResponseDTO;
import com.devices.catalogue.manager.exceptions.DeviceNotFoundException;
import com.devices.catalogue.manager.domain.Device;
import com.devices.catalogue.manager.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeviceService {


    private final DeviceRepository deviceRepository;

    public DeviceResponseDTO addDevice(CreateRequestDto createRequestDto) {
        Device device = Device.fromDto(createRequestDto);
        Device savedDevice = deviceRepository.save(device);
        return DeviceResponseDTO.fromEntity(savedDevice);
    }

    public DeviceResponseDTO getDeviceById(Long id) {
        return deviceRepository.findById(id)
                .map(DeviceResponseDTO::fromEntity).orElseThrow(()->new DeviceNotFoundException(id));
    }

    public List<DeviceResponseDTO> getAllDevices() {
        return deviceRepository.findAll().stream()
                .map(DeviceResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public DeviceResponseDTO updateDevice(Long id, CreateRequestDto createRequestDto) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id));
        device.setName(createRequestDto.getName());
        device.setBrand(createRequestDto.getBrand());
        Device updatedDevice = deviceRepository.save(device);
        return DeviceResponseDTO.fromEntity(updatedDevice);
    }

    public DeviceResponseDTO partialUpdateDevice(Long id, CreateRequestDto createRequestDto) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new DeviceNotFoundException(id));
        if (createRequestDto.getName() != null) {
            device.setName(createRequestDto.getName());
        }
        if (createRequestDto.getBrand() != null) {
            device.setBrand(createRequestDto.getBrand());
        }
        Device updatedDevice = deviceRepository.save(device);
        return DeviceResponseDTO.fromEntity(updatedDevice);
    }

    public void deleteDevice(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new DeviceNotFoundException(id);
        }
        deviceRepository.deleteById(id);
    }

    public List<DeviceResponseDTO> searchDevicesByBrand(String brand) {
        return deviceRepository.findByBrand(brand).stream()
                .map(DeviceResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
