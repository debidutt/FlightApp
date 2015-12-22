package demo.fly.app.page;

/**
 * Created by x086541 on 12/22/2015.
 */

import javax.inject.Inject;
import javax.inject.Singleton;
import mortar.ViewPresenter;
import android.os.Bundle;
import demo.fly.app.core.R;
import demo.fly.app.core.RootModule;
import demo.fly.app.mortarflow.WithModule;
import demo.fly.app.ui.FlightStatusView;
import flow.path.Path;

/**
 * Created by x086541 on 12/22/2015.
 */
@Layout(R.layout.flight_status_view)
@WithModule(FlightStatusPage.Module.class)
public class FlightStatusPage extends Path {

    @dagger.Module(injects = FlightStatusPage.class, addsTo = RootModule.class)
    public static class Module {
//         @Provides
//         List<Chat> provideConversations(Chats chats) {
//         return chats.getAll();
//         }
    }

    @Singleton
    public static class Presenter extends ViewPresenter<FlightStatusView> {
        @Inject
        Presenter() {
        }

        @Override
        public void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            if (!hasView())
                return;
            getView().showFlightStatus();
        }

    }
}
