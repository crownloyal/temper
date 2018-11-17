package com.cit.dominicmbrause.temper;

import android.os.Parcelable;

import net.objecthunter.exp4j.ExpressionBuilder;

class TemperatureUnit implements Temperature {

    public String name;
    private ExpressionBuilder toFunction;
    private ExpressionBuilder fromFunction;

    public TemperatureUnit(String name, String toKelvin, String fromKelvin)  {
        this.name = name;
        this.toFunction = new ExpressionBuilder(toKelvin);
        this.fromFunction = new ExpressionBuilder(fromKelvin);
    }

    public double toKelvin(double value) {
        return toFunction
                .variable("x")
                .build()
                .setVariable("x", value)
                .evaluate();
    }

    public double fromKelvin(double value) {
        return fromFunction
                .variable("x")
                .build()
                .setVariable("x", value)
                .evaluate();
    }
}
