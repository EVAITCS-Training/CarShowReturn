package com.horrorcore.car_show.controllers;

import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import com.horrorcore.car_show.dtos.CarRequest;
import com.horrorcore.car_show.dtos.CarResponse;
import com.horrorcore.car_show.entities.Car;
import com.horrorcore.car_show.enums.EngineType;
import com.horrorcore.car_show.enums.VehicleType;
import com.horrorcore.car_show.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarControllerIntegrationTest {

    @org.springframework.beans.factory.annotation.Value("${local.server.port}")
    private int port;

    @Autowired
    private CarRepository carRepository;

    private static MySQLContainer<?> mysqlContainer;
    private static boolean usingMysql = false;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        // Try to use Testcontainers MySQL when Docker is available; otherwise fall back to H2 in-memory
        if (DockerClientFactory.instance().isDockerAvailable()) {
            mysqlContainer = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.33"))
                    .withDatabaseName("test")
                    .withUsername("test")
                    .withPassword("test");
            mysqlContainer.start();
            usingMysql = true;

            registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
            registry.add("spring.datasource.username", mysqlContainer::getUsername);
            registry.add("spring.datasource.password", mysqlContainer::getPassword);
            registry.add("spring.datasource.driver-class-name", mysqlContainer::getDriverClassName);
            // ensure Hibernate uses appropriate dialect automatically
        } else {
            // H2 fallback
            registry.add("spring.datasource.url", () -> "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=MySQL");
            registry.add("spring.datasource.username", () -> "sa");
            registry.add("spring.datasource.password", () -> "");
            registry.add("spring.datasource.driver-class-name", () -> "org.h2.Driver");
            registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.H2Dialect");
        }

        // Always create/drop schema for tests
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    // Thresholds in milliseconds (adjustable)
    private static final long GET_ALL_THRESHOLD_MS = 1000;
    private static final long CREATE_THRESHOLD_MS = 800;
    private static final long CONCURRENT_MAX_THRESHOLD_MS = 2000;

    @BeforeEach
    void setup() {
        carRepository.deleteAll();
    }

    @org.junit.jupiter.api.AfterAll
    static void afterAll() {
        if (mysqlContainer != null && mysqlContainer.isRunning()) {
            mysqlContainer.stop();
        }
    }

    @Test
    void testGetAllCarsPerformance() {
        // seed 100 cars
        int seed = 100;
        List<Car> entities = new ArrayList<>();
        for (int i = 0; i < seed; i++) {
            Car c = new Car();
            c.setMake("Make" + i);
            c.setModel("Model" + i);
            c.setEngineType(EngineType.V6_ENGINE);
            c.setDoorCount((byte)4);
            c.setVehicleType(VehicleType.SUDAN);
            entities.add(c);
        }
        carRepository.saveAll(entities);

        String url = "http://localhost:" + port + "/api/v1/car/";

        org.springframework.web.client.RestTemplate rt = new org.springframework.web.client.RestTemplate();
        long start = System.nanoTime();
        ResponseEntity<List<CarResponse>> resp = rt.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CarResponse>>() {}
        );
        long durationMs = (System.nanoTime() - start) / 1_000_000;

        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertNotNull(resp.getBody());
        assertEquals(seed, resp.getBody().size());

        System.out.println("GET /api/v1/car time ms: " + durationMs);
        assertTrue(durationMs <= GET_ALL_THRESHOLD_MS, "GET all cars took too long: " + durationMs + "ms");
    }

    @Test
    void testCreateCarPerformance() {
        CarRequest req = new CarRequest("Toyota", "Corolla", EngineType.V6_ENGINE.name(), VehicleType.SUDAN.name(), (byte)4);
        String url = "http://localhost:" + port + "/api/v1/car/create";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CarRequest> entity = new HttpEntity<>(req, headers);

        org.springframework.web.client.RestTemplate rt = new org.springframework.web.client.RestTemplate();
        long start = System.nanoTime();
        ResponseEntity<CarResponse> resp = rt.postForEntity(url, entity, CarResponse.class);
        long durationMs = (System.nanoTime() - start) / 1_000_000;

        assertEquals(HttpStatus.CREATED, resp.getStatusCode());
        assertNotNull(resp.getBody());

        System.out.println("POST /api/v1/car/create time ms: " + durationMs);
        assertTrue(durationMs <= CREATE_THRESHOLD_MS, "Create car took too long: " + durationMs + "ms");
    }

    @Test
    void testConcurrentGetRequestsPerformance() throws ExecutionException, InterruptedException {
        // seed 200 cars
        int seed = 200;
        List<Car> entities = new ArrayList<>();
        for (int i = 0; i < seed; i++) {
            Car c = new Car();
            c.setMake("Make" + i);
            c.setModel("Model" + i);
            c.setEngineType(EngineType.V6_ENGINE);
            c.setDoorCount((byte)4);
            c.setVehicleType(VehicleType.SUDAN);
            entities.add(c);
        }
        carRepository.saveAll(entities);

        int concurrent = 10;
        ExecutorService ex = Executors.newFixedThreadPool(concurrent);

        List<CompletableFuture<Long>> futures = new ArrayList<>();

        IntStream.range(0, concurrent).forEach(i -> {
                org.springframework.web.client.RestTemplate rt = (restTemplate != null) ? restTemplate : new org.springframework.web.client.RestTemplate();
                org.springframework.web.client.RestTemplate rt = new org.springframework.web.client.RestTemplate();
                org.springframework.web.client.RestTemplate rt = (restTemplate != null) ? restTemplate : new org.springframework.web.client.RestTemplate();
                ResponseEntity<List<CarResponse>> resp = rt.exchange(
                        "http://localhost:" + port + "/api/v1/car/",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<CarResponse>>() {}
                );
                long durationMs = (System.nanoTime() - start) / 1_000_000;
                assertEquals(HttpStatus.OK, resp.getStatusCode());
                return durationMs;
            }, ex);
            futures.add(fut);
        });

        List<Long> durations = new ArrayList<>();
        for (CompletableFuture<Long> f : futures) {
            durations.add(f.get());
        }

        ex.shutdownNow();

        long max = durations.stream().mapToLong(Long::longValue).max().orElse(0L);
        double avg = durations.stream().mapToLong(Long::longValue).average().orElse(0.0);

        System.out.println("Concurrent GETs durations ms: " + durations + ", max=" + max + ", avg=" + avg);

        assertTrue(max <= CONCURRENT_MAX_THRESHOLD_MS, "Max concurrent GET time too high: " + max + "ms");
    }
}

