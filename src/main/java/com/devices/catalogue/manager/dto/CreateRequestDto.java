package com.devices.catalogue.manager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(description = "Model retrieve/add devices to/from device catalogue")
public class CreateRequestDto {

    @NotEmpty(message = "Device name is required")
    @ApiModelProperty(notes = "Name of the device", example = "My Device")
    protected String name;

    @NotEmpty(message = "Device brand is required")
    @ApiModelProperty(notes ="Brand of the device", example = "BrandA")
    protected String brand;




}
