package com.cit.dominicmbrause.temper;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TempTextView extends android.support.v7.widget.AppCompatTextView implements Cloneable {

    private TemperatureUnit current;

    public TempTextView(Context context) {
        super(context);
    }
    public TempTextView(Context context, TemperatureUnit unit) {
        super(context);
        assignTemp(unit);
    }
    public TempTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public TempTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // API
    public void assignTemp(TemperatureUnit unit) {
        setCurrent(unit);
        updateText();
    }
    private void updateText() {
        setText(getCurrent().name);
    }

    // S&G
    private void setCurrent(TemperatureUnit unit) {
        current = unit;
    }
    public TemperatureUnit getCurrent() {
        return current;
    }
}
