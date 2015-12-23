package demo.fly.app.activity;

import javax.inject.Inject;

import demo.fly.app.page.FlightStatusPage;
import flow.History;
import mortar.MortarScope;
import mortar.MortarScopeDevHelper;
import mortar.bundler.BundleServiceRunner;
import rx.functions.Action0;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import static android.view.MenuItem.SHOW_AS_ACTION_ALWAYS;
import static mortar.bundler.BundleServiceRunner.getBundleServiceRunner;

public class MenuActivity extends android.app.Activity implements ActionBarOwner.Activity, Flow.Dispatcher {

    private MortarScope activityScope;
    private ActionBarOwner.MenuAction actionBarMenuAction;

    @Inject
    ActionBarOwner actionBarOwner;

    private PathContainerView container;
    private HandlesBack containerAsHandlesBack;
    private FlowDelegate flowDelegate;

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void dispatch(Flow.Traversal traversal, Flow.TraversalCallback callback) {
        Path newScreen = traversal.destination.top();
        String title = newScreen.getClass().getSimpleName();
        ActionBarOwner.MenuAction menu = new ActionBarOwner.MenuAction("Friends", new Action0() {
            @Override
            public void call() {
                Flow.get(MenuActivity.this).set(new MenuPage());
            }
        });
        actionBarOwner.setConfig(new ActionBarOwner.Config(false, !(newScreen instanceof FlightStatusPage), title, menu));

        container.dispatch(traversal, callback);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GsonParceler parceler = new GsonParceler(new Gson());
        @SuppressWarnings("deprecation")
        FlowDelegate.NonConfigurationInstance nonConfig = (FlowDelegate.NonConfigurationInstance) getLastNonConfigurationInstance();

        MortarScope parentScope = MortarScope.getScope(getApplication());

        String scopeName = getLocalClassName() + "-task-" + getTaskId();

        activityScope = parentScope.findChild(scopeName);

        if (activityScope == null) {
            activityScope = parentScope.buildChild().withService(BundleServiceRunner.SERVICE_NAME, new BundleServiceRunner()).build(scopeName);
        }

//        ObjectGraphService.inject(this, this);

        getBundleServiceRunner(activityScope).onCreate(savedInstanceState);

        actionBarOwner.takeView(this);

        setContentView(R.layout.root_layout);
        container = (PathContainerView) findViewById(R.id.container);
        containerAsHandlesBack = (HandlesBack) container;
        flowDelegate = FlowDelegate.onCreate(nonConfig, getIntent(), savedInstanceState, parceler, History.single(new FlightStatusPage()), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        flowDelegate.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        flowDelegate.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        flowDelegate.onPause();
    }

    @SuppressWarnings("deprecation") // https://code.google.com/p/android/issues/detail?id=151346
    @Override
    public Object onRetainNonConfigurationInstance() {
        return flowDelegate.onRetainNonConfigurationInstance();
    }

    @Override
    public Object getSystemService(String name) {
        if (flowDelegate != null) {
            Object flowService = flowDelegate.getSystemService(name);
            if (flowService != null)
                return flowService;
        }

        return activityScope != null && activityScope.hasService(name) ? activityScope.getService(name) : super.getSystemService(name);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        flowDelegate.onSaveInstanceState(outState);
        getBundleServiceRunner(this).onSaveInstanceState(outState);
    }

    /** Inform the view about back events. */
    @Override
    public void onBackPressed() {
        if (!containerAsHandlesBack.onBackPressed())
            super.onBackPressed();
    }

    /** Inform the view about up events. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            return containerAsHandlesBack.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    /** Configure the action bar menu as required by {@link ActionBarOwner.Activity}. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (actionBarMenuAction != null) {
            menu.add(actionBarMenuAction.title).setShowAsActionFlags(SHOW_AS_ACTION_ALWAYS).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    actionBarMenuAction.action.call();
                    return true;
                }
            });
        }
        menu.add("Log Scope Hierarchy").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d("DemoActivity", MortarScopeDevHelper.scopeHierarchyToString(activityScope));
                return true;
            }
        });
        return true;
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