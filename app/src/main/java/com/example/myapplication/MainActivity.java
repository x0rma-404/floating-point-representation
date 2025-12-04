package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    TextInputEditText inputField;
    TextView resultText;
    Button binToDecBtn, decToBinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputField = findViewById(R.id.inputField);
        resultText = findViewById(R.id.resultText);
        binToDecBtn = findViewById(R.id.binToDecBtn);
        decToBinBtn = findViewById(R.id.decToBinBtn);

        binToDecBtn.setOnClickListener(v -> convertBinaryToDecimal());
        decToBinBtn.setOnClickListener(v -> convertDecimalToBinary());
    }

    private void convertBinaryToDecimal() {
        String input = inputField.getText().toString().trim();
        if(input.isEmpty()) {
            resultText.setText("Enter a value!");
            return;
        }
        try {
            BinaryConverter converter = new BinaryConverter(input);
            converter.splitParts();
            resultText.setText(String.valueOf(converter.getResult()));
        } catch (Exception e) {
            resultText.setText("Invalid Binary input!");
        }
    }

    private void convertDecimalToBinary() {
        String input = inputField.getText().toString().trim();
        if(input.isEmpty()) {
            resultText.setText("Enter a value!");
            return;
        }
        try {
            double num = Double.parseDouble(input);
            BinaryReverse converter = new BinaryReverse(num);
            converter.convert(10); // fractional bits
            resultText.setText(converter.getBinaryString());
        } catch (Exception e) {
            resultText.setText("Invalid Decimal input!");
        }
    }
}
