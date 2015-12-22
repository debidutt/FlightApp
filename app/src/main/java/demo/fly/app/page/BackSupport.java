package demo.fly.app.page;

import android.view.View;

import flow.Flow;

/**
 * Created by x086541 on 12/22/2015.
 */
public class BackSupport {

    public static boolean onBackPressed(View childView) {
        if (childView instanceof HandlesBack) {
            if (((HandlesBack) childView).onBackPressed()) {
                return true;
            }
        }
        return Flow.get(childView).goBack();
    }

    private BackSupport() {
    }
}
