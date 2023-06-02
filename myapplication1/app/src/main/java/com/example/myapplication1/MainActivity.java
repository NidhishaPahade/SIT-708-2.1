package com.example.myapplication1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private Spinner sourceUnitSpinner;
    private Spinner destinationUnitSpinner;
    private EditText inputEditText;
    private Button convertButton;
    private TextView resultTextView;

    private String[] lengthUnits = {"Inch", "Foot", "Yard", "Mile"};
    private String[] weightUnits = {"Pound", "Ounce", "Ton"};
    private String[] temperatureUnits = {"Celsius", "Fahrenheit", "Kelvin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sourceUnitSpinner = findViewById(R.id.source_unit_spinner);
        destinationUnitSpinner = findViewById(R.id.destination_unit_spinner);
        inputEditText = findViewById(R.id.input_edit_text);
        convertButton = findViewById(R.id.convert_button);
        resultTextView = findViewById(R.id.result_text_view);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lengthUnits);
        sourceUnitSpinner.setAdapter(adapter);
        destinationUnitSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convert();
            }
        });
    }

    private void convert() {
        String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
        String destinationUnit = destinationUnitSpinner.getSelectedItem().toString();

        String inputValueText = inputEditText.getText().toString();
        if (inputValueText.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(inputValueText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
            return;
        }

        double convertedValue = 0.0;

        if (sourceUnit.equals(destinationUnit)) {
            convertedValue = inputValue;
        } else if (Arrays.asList(lengthUnits).contains(sourceUnit) && Arrays.asList(lengthUnits).contains(destinationUnit)) {
            switch (sourceUnit) {
                case "Inch":
                    if (destinationUnit.equals("Foot"))
                        convertedValue = inputValue / 12.0;
                    else if (destinationUnit.equals("Yard"))
                        convertedValue = inputValue / 36.0;
                    else if (destinationUnit.equals("Mile"))
                        convertedValue = inputValue / 63360.0;
                    break;
                case "Foot":
                    if (destinationUnit.equals("Inch"))
                        convertedValue = inputValue * 12.0;
                    else if (destinationUnit.equals("Yard"))
                        convertedValue = inputValue / 3.0;
                    else if (destinationUnit.equals("Mile"))
                        convertedValue = inputValue / 5280.0;
                    break;
                case "Yard":
                    if (destinationUnit.equals("Inch"))
                        convertedValue = inputValue * 36.0;
                    else if (destinationUnit.equals("Foot"))
                        convertedValue = inputValue * 3.0;
                    else if (destinationUnit.equals("Mile"))
                        convertedValue = inputValue / 1760.0;
                    break;
                case "Mile":
                    if (destinationUnit.equals("Inch"))
                        convertedValue = inputValue * 63360.0;
                    else if (destinationUnit.equals("Foot"))
                        convertedValue = inputValue * 5280.0;
                    else if (destinationUnit.equals("Yard"))
                        convertedValue = inputValue * 1760.0;
                    break;
            }
        } else if (Arrays.asList(weightUnits).contains(sourceUnit) && Arrays.asList(weightUnits).contains(destinationUnit)) {
            switch (sourceUnit) {
                case "Pound":
                    if (destinationUnit.equals("Ounce"))
                        convertedValue = inputValue * 16.0;
                    else if (destinationUnit.equals("Ton"))
                        convertedValue = inputValue / 2000.0;
                    break;
                case "Ounce":
                    if (destinationUnit.equals("Pound"))
                        convertedValue = inputValue / 16.0;
                    else if (destinationUnit.equals("Ton"))
                        convertedValue = inputValue / 32000.0;
                    break;
                case "Ton":
                    if (destinationUnit.equals("Pound"))
                        convertedValue = inputValue * 2000.0;
                    else if (destinationUnit.equals("Ounce"))
                        convertedValue = inputValue * 32000.0;
                    break;
            }
        } else if (Arrays.asList(temperatureUnits).contains(sourceUnit) && Arrays.asList(temperatureUnits).contains(destinationUnit)) {
            switch (sourceUnit) {
                case "Celsius":
                    if (destinationUnit.equals("Fahrenheit"))
                        convertedValue = (inputValue * 1.8) + 32.0;
                    else if (destinationUnit.equals("Kelvin"))
                        convertedValue = inputValue + 273.15;
                    break;
                case "Fahrenheit":
                    if (destinationUnit.equals("Celsius"))
                        convertedValue = (inputValue - 32.0) / 1.8;
                    else if (destinationUnit.equals("Kelvin"))
                        convertedValue = (inputValue + 459.67) * 5.0 / 9.0;
                    break;
                case "Kelvin":
                    if (destinationUnit.equals("Celsius"))
                        convertedValue = inputValue - 273.15;
                    else if (destinationUnit.equals("Fahrenheit"))
                        convertedValue = (inputValue * 9.0 / 5.0) - 459.67;
                    break;
            }
        }

        resultTextView.setText(String.valueOf(convertedValue));
    }
}
