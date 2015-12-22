package demo.fly.app.core;

import android.content.Context;
import android.util.AttributeSet;

import demo.fly.app.mortarflow.MortarContextFactory;
import demo.fly.app.page.FramePathContainerView;
import demo.fly.app.page.SimplePathContainer;
import flow.path.Path;

/**
 * Created by x086541 on 12/22/2015.
 */
public class ScreenSwitcherFrame extends FramePathContainerView {
    public ScreenSwitcherFrame(Context context, AttributeSet attrs) {
        super(context, attrs, new SimplePathContainer(R.id.screen_switcher_tag,
                Path.contextFactory(new MortarContextFactory())));
    }
}
