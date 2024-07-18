package com.devices.catalogue.manager.services;

import com.devices.catalogue.manager.dto.CreateRequestDto;
import com.devices.catalogue.manager.dto.DeviceResponseDTO;
import com.devices.catalogue.manager.exceptions.DeviceNotFoundException;
import com.devices.catalogue.manager.domain.Device;
import com.devices.catalogue.manager.repositories.DeviceRepository;
import com.devices.catalogue.manager.service.DeviceService;
import com.devices.catalogue.manager.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceService deviceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddDevice() {
        Device device = new Device();
        device.setName("Test Device");
        device.setBrand("Test Brand");
        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        CreateRequestDto createRequestDto = new CreateRequestDto();
        createRequestDto.setName("Test Device");
        createRequestDto.setBrand("Test Brand");

        CreateRequestDto savedDevice = deviceService.addDevice(createRequestDto);
        assertNotNull(savedDevice);
        assertEquals("Test Device", savedDevice.getName());
        assertEquals("Test Brand", savedDevice.getBrand());
    }

    @Test
    public void testGetDeviceById() {
        Device device = new Device();
        device.setId(1L);

        device.setCreationTime(TestUtils.testDateTime());
        device.setBrand("my brand");
        device.setName("my device");
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        DeviceResponseDTO foundDevice = deviceService.getDeviceById(1L);
        assertThat(foundDevice).isNotNull();
        assertThat(foundDevice.getId()).isEqualTo(1L);
    }

    @Test
    public void testGetAllDevices() {
        Device device1 = new Device();
        Device device2 = new Device();
        when(deviceRepository.findAll()).thenReturn(List.of(device1, device2));

        List<DeviceResponseDTO> devices = deviceService.getAllDevices();
        assertEquals(2, devices.size());
    }

    @Test
    public void testUpdateDevice() {
        Device existingDevice = new Device();
        existingDevice.setId(1L);
        when(deviceRepository.findById(1L)).thenReturn(Optional.of(existingDevice));

        CreateRequestDto createRequestDto = new CreateRequestDto();
        createRequestDto.setName("Updated Device");
        createRequestDto.setBrand("Updated Brand");

        Device updatedDevice = new Device();
        updatedDevice.setId(1L);
        updatedDevice.setName("Updated Device");
        updatedDevice.setBrand("Updated Brand");
        updatedDevice.setDeviceCreationTime();
        when(deviceRepository.save(existingDevice)).thenReturn(updatedDevice);

        DeviceResponseDTO updateDevice = deviceService.updateDevice(1L, createRequestDto);
        assertNotNull(updateDevice);
        assertEquals("Updated Device", updatedDevice.getName());
        assertEquals("Updated Brand", updatedDevice.getBrand());
    }

    @Test
    public void testUpdateDeviceNotFound() {
        CreateRequestDto createRequestDto = new CreateRequestDto();
        when(deviceRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(DeviceNotFoundException.class, () -> {
            deviceService.updateDevice(1L, createRequestDto);
        });
        }
    }