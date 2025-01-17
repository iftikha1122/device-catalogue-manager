package com.devices.catalogue.manager.repositories;


import com.devices.catalogue.manager.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByBrand(String brand);
}
