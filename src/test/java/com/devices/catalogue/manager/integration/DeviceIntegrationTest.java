package com.devices.catalogue.manager.integration;

import com.devices.catalogue.manager.dto.CreateRequestDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


import org.springframework.beans.factory.annotation.Value;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DeviceIntegrationTest {

    @Value("${local.server.port}")
    private int port;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void testAddDevice() {
        CreateRequestDto createRequestDto = new CreateRequestDto();
        createRequestDto.setName("Integration Test Device");
        createRequestDto.setBrand("Integration Test Brand");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createRequestDto)
                .port(port)
                .when()
                .post("/devices")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo("Integration Test Device"))
                .body("brand", equalTo("Integration Test Brand"));
    }

    @Test
    public void testAddDeviceValidationError() {
        CreateRequestDto createRequestDto = new CreateRequestDto();
        createRequestDto.setName("");
        createRequestDto.setBrand("");

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createRequestDto)
                .port(port)
                .when()
                .post("/devices")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo("Validation error"))
                .body("errors.name", equalTo("Device name is required"))
                .body("errors.brand", equalTo("Device brand is required"));
    }

    @Test
    public void testGetDeviceById() {
        CreateRequestDto createRequestDto = new CreateRequestDto();
        createRequestDto.setName("Integration Test Device");
        createRequestDto.setBrand("Integration Test Brand");

        var response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createRequestDto)
                .port(port)
                .when()
                .post("/devices")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract().response();

        long deviceId = response.jsonPath().getLong("id");

        given()
                .port(port)
                .when()
                .get("/devices/" + deviceId)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo("Integration Test Device"))
                .body("brand", equalTo("Integration Test Brand"));
    }

    @Test
    public void testGetDeviceByIdNotFound() {
        given()
                .port(port)
                .when()
                .get("/devices/999")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo("Device not found with id: 999"));
    }

    @Test
    public void testGetAllDevices() {
        given()
                .port(port)
                .when()
                .get("/devices")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void testSearchDevicesByBrand() {
        given()
                .port(port)
                .param("brand", "Integration Test Brand")
                .when()
                .get("/devices/search")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}
