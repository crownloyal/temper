package com.cit.dominicmbrause.temper;

import net.objecthunter.exp4j.ExpressionBuilder;

class TemperatureUnit implements Temperature {

    public String name;
    private ExpressionBuilder toFunction;
    private ExpressionBuilder fromFunction;

    public TemperatureUnit(String name, String toKelvin, String fromKelvin)  {
        this.name = name;
        this.toFunction = new ExpressionBuilder(toKelvin);
    }

    public String toKelvin(double value) {
        return toFunction
                .variable("x")
                .build()
                .setVariable("x", value)
                .toString(); // this is dirty!
    }

    public String fromKelvin(double value) {
        return fromFunction
                .variable("x")
                .build()
                .setVariable("x", value)
                .toString();
    }
}
