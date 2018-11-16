package com.cit.dominicmbrause.temper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TemperatureUnit kelvin = new TemperatureUnit("Kelvin", "x + 0", "x + 0");
    TemperatureUnit celsius = new TemperatureUnit("Celsius", "x + 273.15", "x - 273.15");
    TemperatureUnit fahr = new TemperatureUnit("Fahrenheit", "9/5 * (x + 273.15) + 32", "9/5 * (x − 273.15) + 32");


    public void convert(View view) {
        double convertedLeft = Double.parseDouble(fahr.toKelvin(this.getInput(view)));
        double result = Double.parseDouble(celsius.fromKelvin(convertedLeft));

        Log.i("DEBUG:", String.valueOf(result));

        updateResult(view, result, fahr);
    }
    private void updateResult(View view, double result, TemperatureUnit unit) {
        TextView textLeft = (TextView) findViewById(R.id.TextView__result);
        textLeft.setText(unit.name + " " + String.valueOf(result));
    }
    private double getInput(View view) {
        EditText input = (EditText) findViewById(R.id.editText__input);
        double inputValue = Double.parseDouble(input.getText().toString());

        return inputValue;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set left and right
        TextView textLeft = (TextView) findViewById(R.id.textView__left);
        TextView textRight = (TextView) findViewById(R.id.textView__right);
        textLeft.setText(celsius.name);
        textRight.setText(fahr.name);

        // Prepare
        Button convertButton = findViewById(R.id.button__convert);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convert(v);
            }
        });
    }


}
