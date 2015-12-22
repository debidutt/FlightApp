package demo.fly.app.page;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by x086541 on 12/22/2015.
 */
@Retention(RUNTIME) @Target(TYPE) public @interface Layout {
    int value();
}
