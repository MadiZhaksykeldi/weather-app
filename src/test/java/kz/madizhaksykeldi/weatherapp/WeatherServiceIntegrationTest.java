package kz.madizhaksykeldi.weatherapp;

import kz.madizhaksykeldi.weatherapp.dto.ForecastDto;
import kz.madizhaksykeldi.weatherapp.dto.WeatherDto;
import kz.madizhaksykeldi.weatherapp.exception.ApiException;
import kz.madizhaksykeldi.weatherapp.service.WeatherService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@SpringBootTest
@AutoConfigureMockRestServiceServer
public class WeatherServiceIntegrationTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WeatherService weatherService; // The service containing your methods

    @Autowired
    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.bindTo(restTemplate).build();
    }

    @Test
    public void testGetCurrentWeather_ApiError() {
        String cityName = "InvalidCity";
        String expectedUrl = String.format("https://api.weatherapi.com/v1/current.json?key=%s&q=%s", "572fa753a7f649babb9113720242711", cityName);

        mockServer.expect(requestTo(expectedUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        // Expect exception
        ApiException exception = assertThrows(ApiException.class, () -> weatherService.getCurrentWeather(cityName));
        assertTrue(exception.getMessage().contains("Error fetching weather data"));
    }

}

