package demo.fly.app.mortarflow;

import android.content.res.Resources;

/**
 * Created by x086541 on 12/22/2015.
 */
public abstract class ModuleFactory<T> {
    protected abstract Object createDaggerModule(Resources resources, T screen);
}
