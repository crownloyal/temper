package com.cit.dominicmbrause.temper;

import net.objecthunter.exp4j.ExpressionBuilder;

interface Temperature {

    String name = null;
    ExpressionBuilder functionbase = null;

    public double toKelvin(double value);
    public double fromKelvin(double value);

}
