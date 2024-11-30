package kz.madizhaksykeldi.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDto {

    private Location location;
    private Current current;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Location {
        private String name;
        private String country;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Current {
        private double temp_c;
        private double wind_kph;
        private int humidity;
        private String last_updated;
        private Condition condition;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Condition {
            private String text;
        }
    }

}
