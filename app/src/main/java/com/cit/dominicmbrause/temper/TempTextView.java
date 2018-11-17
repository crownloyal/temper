package com.cit.dominicmbrause.temper;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;

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

    // S&G
    private void setCurrent(TemperatureUnit unit) {
        current = unit;
    }
    public TemperatureUnit getCurrent() {
        return current;
    }
    private void updateText() {
        setText(getCurrent().name);
    }
}
