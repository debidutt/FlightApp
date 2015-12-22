package demo.fly.app.activity;

import javax.inject.Inject;
import mortar.MortarScope;
import rx.functions.Action0;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.gson.Gson;
import demo.fly.app.android.ActionBarOwner;
import demo.fly.app.core.R;
import demo.fly.app.page.GsonParceler;
import demo.fly.app.page.HandlesBack;
import demo.fly.app.page.MenuPage;
import flow.Flow;
import flow.FlowDelegate;
import flow.path.Path;
import flow.path.PathContainerView;

public class MenuActivity extends android.app.Activity implements ActionBarOwner.Activity, Flow.Dispatcher {

    @Inject
    ActionBarOwner actionBarOwner;

    private ActionBarOwner.MenuAction actionBarMenuAction;
    private MortarScope activityScope;
    private FlowDelegate flowDelegate;
    private PathContainerView container;
    private HandlesBack containerAsHandlesBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        actionBarOwner.takeView(this);

        FlowDelegate.NonConfigurationInstance nonConfig = (FlowDelegate.NonConfigurationInstance) getLastNonConfigurationInstance();
        GsonParceler parceler = new GsonParceler(new Gson());

        setContentView(R.layout.root_layout);
        container = (PathContainerView) findViewById(R.id.container);
        containerAsHandlesBack = (HandlesBack) container;
        //TODO Main Menu : Next Screen
//        flowDelegate = FlowDelegate.onCreate(nonConfig, getIntent(), savedInstanceState, parceler, History.single(new FlightStatusPage()), this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        flowDelegate.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        flowDelegate.onResume();
    }

    @Override
    protected void onPause() {
//        flowDelegate.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        actionBarOwner.dropView(this);
        actionBarOwner.setConfig(null);

        // activityScope may be null in case isWrongInstance() returned true in onCreate()
        if (isFinishing() && activityScope != null) {
            activityScope.destroy();
            activityScope = null;
        }

        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
        Path newScreen = traversal.destination.top();
        String title = newScreen.getClass().getSimpleName();
        ActionBarOwner.MenuAction menu = new ActionBarOwner.MenuAction("Welcome", new Action0() {
            @Override
            public void call() {
                Flow.get(MenuActivity.this).set(new MenuPage());
            }
        });

        //TODO: Initiate Navigation

//         actionBarOwner.setConfig(new ActionBarOwner.Config(false, !(newScreen instanceof ), title, menu));
//
//         container.dispatch(traversal, callback);
    }

    @Override
    public void setShowHomeEnabled(boolean enabled) {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
    }

    @Override
    public void setUpButtonEnabled(boolean enabled) {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(enabled);
        actionBar.setHomeButtonEnabled(enabled);
    }

    @Override
    public void setTitle(CharSequence title) {
        getActionBar().setTitle(title);
    }

    @Override
    public void setMenu(ActionBarOwner.MenuAction action) {
        if (action != actionBarMenuAction) {
            actionBarMenuAction = action;
            invalidateOptionsMenu();
        }
    }
}
