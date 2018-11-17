package com.cit.dominicmbrause.temper;

import android.os.strictmode.UnbufferedIoViolation;

import java.util.ArrayList;

class UnitSelectionController {

    private static ArrayList<TemperatureUnit> temps = new ArrayList<>();
    private static ArrayList<Integer> counter = new ArrayList<>();


    UnitSelectionController() {}

    private int getCount() {
        return temps.size();
    }

    TemperatureUnit getNext() {
        if(getCount() < 1) {
            throw new Error("Error: No items have been added to the unit queue.");
        }

        int next = identifyUnused();
        counter.set(next, counter.get(next) + 1);
        return temps.get(next);
    }
    private int identifyUnused() {
        int index = 0;

        for(int i = 0; i < counter.size(); i++) {
            int temp = counter.get(i);
            int current = counter.get(index);
            if(temp < current) {
                index = i;
            }
        }

        return index;
    }


    void addUnit(TemperatureUnit temp) {
        temps.add(temp);
        counter.add(0);
    }
}
