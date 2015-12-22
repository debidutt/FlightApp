package demo.fly.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;

import java.util.List;

import javax.inject.Inject;

import demo.fly.app.page.FlightStatusPage;

/**
 * Created by x086541 on 12/22/2015.
 */
public class FlightStatusView extends FrameLayout {

    @Inject
    FlightStatusPage.Presenter presenter;

    public FlightStatusView(Context context) {
        super(context);
    }

    public FlightStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlightStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public void showFlightStatus() {
        Log.d("Show Flight Status Screen","Yes");
    }

}
