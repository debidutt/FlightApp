package demo.fly.app.page;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import demo.fly.app.core.R;
import flow.Flow;
import flow.path.Path;
import flow.path.PathContainer;
import flow.path.PathContainerView;

/**
 * Created by x086541 on 12/22/2015.
 */
public class FramePathContainerView extends FrameLayout implements HandlesBack, PathContainerView {

    private final PathContainer container;
    private boolean disabled;

    public FramePathContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, new SimplePathContainer(R.id.screen_switcher_tag, Path.contextFactory()));
    }

    protected FramePathContainerView(Context context, AttributeSet attrs, PathContainer container) {
        super(context, attrs);
        this.container = container;
    }

    @Override
    public ViewGroup getContainerView() {
        return this;
    }

    @Override
    public boolean onBackPressed() {
        return BackSupport.onBackPressed(getCurrentChild());
    }

    @Override
    public ViewGroup getCurrentChild() {
        return (ViewGroup) getContainerView().getChildAt(0);
    }

    @Override
    public void dispatch(Flow.Traversal traversal, final Flow.TraversalCallback callback) {
        disabled = true;
        container.executeTraversal(this, traversal, new Flow.TraversalCallback() {
            @Override
            public void onTraversalCompleted() {
                callback.onTraversalCompleted();
                disabled = false;
            }
        });
    }
}
