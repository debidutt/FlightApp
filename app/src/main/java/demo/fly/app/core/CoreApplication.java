package demo.fly.app.core;

import android.app.Application;

import mortar.MortarScope;

/**
 * Created by x086541 on 12/21/2015.
 */
public class CoreApplication extends Application {

    private MortarScope rootScope;

    @Override
    public Object getSystemService(String name) {
        if (rootScope == null)
            rootScope = MortarScope.buildRootScope().build("Root");

        return rootScope.hasService(name) ? rootScope.getService(name) : super.getSystemService(name);
    }
}
