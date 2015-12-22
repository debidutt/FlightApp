package demo.fly.app.core;

/**
 * Created by x086541 on 12/22/2015.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import demo.fly.app.activity.MenuActivity;
import demo.fly.app.android.ActionBarOwner;
import demo.fly.app.model.StatusOfFlight;
import demo.fly.app.page.GsonParceler;
import flow.StateParceler;

/**
 * Defines app-wide singletons.
 */
@Module(
        includes = {ActionBarOwner.ActionBarModule.class, StatusOfFlight.Module.class},
        injects = MenuActivity.class,
        library = true)
public class RootModule {
    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides @Singleton
    StateParceler provideParcer(Gson gson) {
        return new GsonParceler(gson);
    }

    //TODO : Service Implementation
//    @Provides @Singleton QuoteService provideFlightStatusService() {
//        RestAdapter restAdapter =
//                new RestAdapter.Builder().setEndpoint("http://www.iheartquotes.com/api/v1/")
//                        .setConverter(new GsonConverter(new Gson()))
//                        .build();
//        return restAdapter.create(QuoteService.class);
//    }
}
