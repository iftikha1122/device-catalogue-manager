package com.devices.catalogue.manager.repositories;

import com.devices.catalogue.manager.domain.Device;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class DeviceRepository {
    private final ConcurrentHashMap<Long, Device> devices = new ConcurrentHashMap<>();
    private final AtomicLong  currentId = new AtomicLong();

    public Device save(Device device) {
        if (device.getId() == null) {
            synchronized (this) {
                device.setId(currentId.incrementAndGet());
            }
        }
        devices.put(device.getId(), device);
        return device;
    }

    public Optional<Device> findById(Long id) {
        return Optional.ofNullable(devices.get(id));
    }

    public List<Device> findAll() {
        return devices.values().stream().toList();
    }

    public void deleteById(Long id) {
        devices.remove(id);
    }

    public List<Device> findByBrand(String brand) {
        return devices.values().stream()
                .filter(device -> brand.equals(device.getBrand()))
                .toList();
    }

    public Device updateDevice(Long id, Device updatedDevice) {
        Device existingDevice = devices.get(id);
        if (existingDevice != null) {
            synchronized (existingDevice) {
                existingDevice.setName(updatedDevice.getName());
                existingDevice.setBrand(updatedDevice.getBrand());
                existingDevice.setCreationTime(updatedDevice.getCreationTime());
            }
            return existingDevice;
        } else {
            throw new RuntimeException("Device not found with id: " + id);
        }
    }

    public Device partialUpdateDevice(Long id, Device updatedDevice) {
        Device existingDevice = devices.get(id);
        if (existingDevice != null) {
            synchronized (existingDevice) {
                if (updatedDevice.getName() != null) {
                    existingDevice.setName(updatedDevice.getName());
                }
                if (updatedDevice.getBrand() != null) {
                    existingDevice.setBrand(updatedDevice.getBrand());
                }
                if (updatedDevice.getCreationTime() != null) {
                    existingDevice.setCreationTime(updatedDevice.getCreationTime());
                }
            }
            return existingDevice;
        } else {
            throw new RuntimeException("Device not found with id: " + id);
        }
    }
}
