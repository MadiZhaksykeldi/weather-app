package kz.madizhaksykeldi.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForecastDto {

    private Location location;
    private Forecast forecast;

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
    public static class Forecast {
        private List<ForecastDay> forecastday;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ForecastDay {
            private String date;
            private Day day;

            @Data
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Day {
                private double maxtemp_c;
                private double mintemp_c;
                private double avgtemp_c;
                private Condition condition;

                @Data
                @NoArgsConstructor
                @AllArgsConstructor
                public static class Condition {
                    private String text;
                }
            }
        }
    }

}
