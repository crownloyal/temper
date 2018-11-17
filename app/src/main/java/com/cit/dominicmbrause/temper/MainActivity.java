package com.cit.dominicmbrause.temper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
        TextView textResult = findViewById(R.id.textView__result);
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
    private void revealShare(View view) {
        TextView textShare = findViewById(R.id.textView__share);
        textShare.setVisibility(View.VISIBLE);
    }
    private void share(View view) {
        TextView textShare = findViewById(R.id.textView__share);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        TempTextView textLeft = findViewById(R.id.textView__left);
        TempTextView textRight = findViewById(R.id.textView__right);
        TextView input = findViewById(R.id.editText__input);
        TextView result = findViewById(R.id.textView__result);

        TemperatureUnit temp1 = textLeft.getCurrent();
        TemperatureUnit temp2 = textRight.getCurrent();
        String value1 = input.getText().toString();
        String value2 = result.getText().toString();

        sharingIntent.setType("text/plain");
        String shareBody = String.format("Did you know that %s %s are equal to %s %s", value1, temp1.name, value2, temp2.name);
        String shareSub = "Check out this conversion!";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share using"));
    }


    private void nextUnit(View v, TempTextView text) {
        text.assignTemp(unitSelector.getNext());
    }
    private void setupController() {
        TemperatureUnit kelvin = new TemperatureUnit("Kelvin", "x + 0", "x + 0");
        TemperatureUnit celsius = new TemperatureUnit("Celsius", "x + 273.15", "x - 273.15");
        TemperatureUnit fahr = new TemperatureUnit("Fahrenheit", "(x - 32) * (5/9) + 273.15", "(x - 273.15) * (9/5) + 32");
        unitSelector.addUnit(kelvin);
        unitSelector.addUnit(celsius);
        unitSelector.addUnit(fahr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check for existing data
        if (!(savedInstanceState == null)) {
            final EditText input = findViewById(R.id.editText__input);
            final TextView result = findViewById(R.id.textView__result);
            String resultValue = savedInstanceState.getString("RESULT");
            String inputValue = savedInstanceState.getString("INPUT");

            input.setText(inputValue);
            result.setText(resultValue);
        }

        // Prepare Units
        setupController();

        // Set left and right defaults
        final TempTextView textLeft = findViewById(R.id.textView__left);
        final TempTextView textRight = findViewById(R.id.textView__right);
        textLeft.assignTemp(unitSelector.getNext());
        textRight.assignTemp(unitSelector.getNext());
        final TextView textShare = findViewById(R.id.textView__share);
        textShare.setVisibility(View.GONE); // hide share item on init

        // Set up tap events
        Button convertButton = findViewById(R.id.button__convert);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convert(v);
                revealShare(v);
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

        textShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(v);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        final EditText input = findViewById(R.id.editText__input);
        final TextView result = findViewById(R.id.textView__result);

        savedInstanceState.putString("INPUT", input.getText().toString());
        savedInstanceState.putString("RESULT", result.getText().toString());

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
