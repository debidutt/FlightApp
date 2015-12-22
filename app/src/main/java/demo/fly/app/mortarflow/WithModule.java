package demo.fly.app.mortarflow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by x086541 on 12/22/2015.
 */
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME)
public @interface WithModule {
    Class<?> value();
}

