package com.cit.dominicmbrause.temper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    static UnitSelectionController unitSelector = new UnitSelectionController();

    public void convert(View view) {
        TempTextView leftSide = findViewById(R.id.textView__left);
        TempTextView rightSide = findViewById(R.id.textView__right);
        double convertedToKelvin = leftSide.getCurrent().toKelvin(getInput(view));
        double result = rightSide.getCurrent().fromKelvin(convertedToKelvin);

        // Conversion magic
        DecimalFormat df = new DecimalFormat("####0.00");
        double formattedResult = Double.valueOf(df.format(result));

        updateResult(view, formattedResult, rightSide.getCurrent());
    }
    private void updateResult(View view, double result, TemperatureUnit unit) {
        TextView textResult = findViewById(R.id.TextView__result);
        textResult.setText(String.format("%s %s°", unit.name, String.valueOf(result)));
    }
    private double getInput(View view) {
        EditText input = findViewById(R.id.editText__input);
        String value = input.getText().toString();

        return Double.parseDouble(value);
    }

    private void swapSides(View view) throws CloneNotSupportedException {
        TempTextView textLeft = findViewById(R.id.textView__left);
        TempTextView textRight = findViewById(R.id.textView__right);

        TempTextView temp = (TempTextView) textLeft.clone();
        textLeft.assignTemp(textRight.getCurrent());
        textRight.assignTemp(temp.getCurrent());
    }

    private void nextUnit(View v, TempTextView text) {
        text.assignTemp(unitSelector.getNext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prepare Units
        TemperatureUnit kelvin = new TemperatureUnit("Kelvin", "x + 0", "x + 0");
        TemperatureUnit celsius = new TemperatureUnit("Celsius", "x + 273.15", "x - 273.15");
        TemperatureUnit fahr = new TemperatureUnit("Fahrenheit", "(x + 273.15) * (9/5) - 32", "(x - 273.15) * (9/5) + 32");
        unitSelector.addUnit(kelvin);
        unitSelector.addUnit(celsius);
        unitSelector.addUnit(fahr);

        // Set left and right defaults
        final TempTextView textLeft = findViewById(R.id.textView__left);
        final TempTextView textRight = findViewById(R.id.textView__right);
        textLeft.assignTemp(unitSelector.getNext());
        textRight.assignTemp(unitSelector.getNext());


        // Set up tap events
        Button convertButton = findViewById(R.id.button__convert);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convert(v);
            }
        });

        TextView toggleSides = findViewById(R.id.textView__to);
        toggleSides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    swapSides(v);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        textLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextUnit(v, textLeft);
            }
        });

        textRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextUnit(v, textRight);
            }
        });
    }

}
