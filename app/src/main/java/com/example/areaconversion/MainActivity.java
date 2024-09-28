package com.example.areaconversion;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerInputUnit, spinnerOutputUnit;
    private EditText inputValue;
    private TextView resultText;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerInputUnit = findViewById(R.id.spinner_input_unit);
        spinnerOutputUnit = findViewById(R.id.spinner_output_unit);
        inputValue = findViewById(R.id.input_value);
        resultText = findViewById(R.id.result_text);
        convertButton = findViewById(R.id.convert_button);

        // Add conversion options to the spinners
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.area_units, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerInputUnit.setAdapter(adapter);
        spinnerOutputUnit.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertArea();
            }
        });
    }

    private void convertArea() {
        String inputUnit = spinnerInputUnit.getSelectedItem().toString();
        String outputUnit = spinnerOutputUnit.getSelectedItem().toString();
        String inputValueStr = inputValue.getText().toString();

        if (inputValueStr.isEmpty()) {
            Toast.makeText(this, "Please enter an area value", Toast.LENGTH_SHORT).show();
            return;
        }

        double inputArea = Double.parseDouble(inputValueStr);

        if (inputUnit.equals(outputUnit)) {
            resultText.setText("Result: " + inputArea + " " + outputUnit);
        } else {
            double result = convert(inputArea, inputUnit, outputUnit);
            resultText.setText("Result: " + result + " " + outputUnit);
        }
    }

    private double convert(double area, String fromUnit, String toUnit) {
        // Convert to square meters first
        double areaInSquareMeters = 0;
        switch (fromUnit) {
            case "Square Meters":
                areaInSquareMeters = area;
                break;
            case "Square Centimeters":
                areaInSquareMeters = area / 10000;
                break;
            case "Square Feet":
                areaInSquareMeters = area / 10.764;
                break;
            case "Square Inches":
                areaInSquareMeters = area / 1550.0031;
                break;
        }

        // Convert from square meters to the output unit
        switch (toUnit) {
            case "Square Meters":
                return areaInSquareMeters;
            case "Square Centimeters":
                return areaInSquareMeters * 10000;
            case "Square Feet":
                return areaInSquareMeters * 10.764;
            case "Square Inches":
                return areaInSquareMeters * 1550.0031;
        }
        return 0;
    }
}