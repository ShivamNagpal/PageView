package com.nagpal.shivam.pageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class PageView extends android.support.v7.widget.AppCompatTextView {

    final private float defaultSize;
    private float scaleFactor = 1.0f;
    private float zoomFactor;
    private ScaleGestureDetector scaleGestureDetector;

    public PageView(Context context) {
        super(context);
        defaultSize = this.getTextSize();
        initGestures();
    }

    public PageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        defaultSize = this.getTextSize();
        initAttributes(attrs);
        initGestures();
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defaultSize = this.getTextSize();
        initAttributes(attrs);
        initGestures();
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PageView, 0, 0);
        try {
            zoomFactor = typedArray.getFloat(R.styleable.PageView_zoomFactor, 1.0f);
        } finally {
            typedArray.recycle();
        }
    }

    private void initGestures() {
        this.setHorizontallyScrolling(true);
        this.setHorizontalScrollBarEnabled(true);
        this.setVerticalScrollBarEnabled(true);
        this.setMovementMethod(ScrollingMovementMethod.getInstance());

        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                scaleFactor *= detector.getScaleFactor();
                scaleFactor = Math.max(1.0f, Math.min(scaleFactor, zoomFactor));
                setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultSize * scaleFactor);
                return true;
            }
        });

    }

    public float getZoomFactor() {
        return zoomFactor;
    }

    public void setZoomFactor(float zoomFactor) {
        this.zoomFactor = zoomFactor;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }
}
