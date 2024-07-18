package com.devices.catalogue.manager.dto;

import com.devices.catalogue.manager.domain.Device;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceResponseDTO extends CreateRequestDto{

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String creationTime;

    public static DeviceResponseDTO fromEntity(Device device) {
        DeviceResponseDTO dto = new DeviceResponseDTO();
        dto.setId(device.getId());
        dto.setName(device.getName());
        dto.setBrand(device.getBrand());
        dto.setCreationTime(device.creationTimeToString());
        return dto;
    }

}
