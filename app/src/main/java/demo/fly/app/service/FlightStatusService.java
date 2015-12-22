package demo.fly.app.service;

import retrofit.http.GET;

/**
 * Created by x086541 on 12/22/2015.
 */
public interface FlightStatusService {

    class FlightStatus {
        public final String flightStatus;
        public FlightStatus(String flightStatus) {
            this.flightStatus = flightStatus;
        }
    }

    @GET("/random?format=json&source=zippy&show_permalink=false&show_source=false") //
    FlightStatus getFlightStatus();
}
