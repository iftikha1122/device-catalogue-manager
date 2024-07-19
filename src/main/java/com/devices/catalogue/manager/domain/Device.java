package com.devices.catalogue.manager.domain;

import com.devices.catalogue.manager.dto.CreateRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device {
    private final static DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");;

    private Long id;

    private String name;

    private String brand;

    private LocalDateTime creationTime;


    public static Device fromDto(CreateRequestDto dto) {
     var device = Device
                .builder()
                .brand(dto.getBrand())
                .name(dto.getName())
                .build();
     device.setDeviceCreationTime();
     return device;

    }
    public void setDeviceCreationTime(){
        if(Objects.isNull(this.creationTime)) {
            creationTime = LocalDateTime.now();
        }
    }

    public String creationTimeToString(){
    return   Optional.ofNullable(this.getCreationTime())
            .map(creationTime->creationTime.format(DATE_TIME_FORMAT))
            .orElse("");
    }
}

