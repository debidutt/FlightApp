package demo.fly.app.model;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;
import demo.fly.app.service.FlightStatusService;

/**
 * Created by x086541 on 12/22/2015.
 */

@Singleton
public class StatusOfFlight {
    private String status;

    @Inject
    StatusOfFlight(Executor messagePollThread, FlightStatusService service) {

    }

    @dagger.Module(injects = FlightStatusService.FlightStatus.class, library = true, complete = false)
    public static class Module {

        @Provides
        @Singleton Executor provideMessagePollThread() {
            return Executors.newSingleThreadExecutor();
        }
    }
}
