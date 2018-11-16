package com.cit.dominicmbrause.temper;

import net.objecthunter.exp4j.ExpressionBuilder;

interface Temperature {

    String name = null;
    ExpressionBuilder functionbase = null;

    public String toKelvin(double value);
    public String fromKelvin(double value);

}
