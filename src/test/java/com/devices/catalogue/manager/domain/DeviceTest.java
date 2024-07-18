package com.devices.catalogue.manager.domain;

import com.devices.catalogue.manager.dto.CreateRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeviceTest {
    @Test
    public void testFromDto_NonNullValues() {
        CreateRequestDto dto = CreateRequestDto.builder()
                .name("Test Device")
                .brand("Test Brand")
                .build();
        Device device = Device.fromDto(dto);
        assertThat(device).isNotNull();
        assertThat(device.getName()).isEqualTo("Test Device");
        assertThat(device.getBrand()).isEqualTo("Test Brand");
        assertThat(device.getCreationTime()).isNotNull();
    }
}
