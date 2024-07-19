package com.devices.catalogue.manager.repositories;

import com.devices.catalogue.manager.domain.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class DeviceRepositoryTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    public void testSaveAndFindById() {
        Device device = new Device();
        device.setName("Test Device");
        device.setBrand("Test Brand");

        Device savedDevice = deviceRepository.save(device);
        assertNotNull(savedDevice);
        assertNotNull(savedDevice.getId());

        Device foundDevice = deviceRepository.findById(savedDevice.getId()).orElse(null);
        assertNotNull(foundDevice);
        assertEquals("Test Device", foundDevice.getName());
        assertEquals("Test Brand", foundDevice.getBrand());
    }

    @Test
    public void testFindByBrand() {
        Device device = new Device();
        device.setName("Test Device");
        device.setBrand("Test Brand");
        deviceRepository.save(device);

        List<Device> devices = deviceRepository.findByBrand("Test Brand");
        assertEquals(1, devices.size());
        assertEquals("Test Brand", devices.get(0).getBrand());
    }

    @Test
    public void testFindAll() {
        Device device1 = new Device();
        device1.setName("Device 1");
        device1.setBrand("Brand 1");
        deviceRepository.save(device1);

        Device device2 = new Device();
        device2.setName("Device 2");
        device2.setBrand("Brand 2");
        deviceRepository.save(device2);

        List<Device> devices = deviceRepository.findAll();
        assertEquals(2, devices.size());
    }
}
