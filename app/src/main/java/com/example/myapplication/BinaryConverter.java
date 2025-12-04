package com.example.myapplication;

public class BinaryConverter {

    private String input;      // input: binary (8-bit) və ya decimal
    private double result;     // decimal nəticə
    private String binary8;    // 8-bit binary

    public BinaryConverter(String input) {
        this.input = input.trim();
    }

    // MainActivity-də splitParts() çağırışı üçün
    public void splitParts() {
        if (input.matches("[01]{8}")) {
            convertBinaryToDecimal(input);
        } else {
            double decimal = Double.parseDouble(input);
            convertDecimalToBinary(decimal);
            result = decimal;
        }
    }

    // Decimal → 8-bit floating-point
    private void convertDecimalToBinary(double decimal) {
        int sign = decimal < 0 ? 1 : 0;
        double absVal = Math.abs(decimal);

        // Normalization
        int exponentActual = 0;
        double mantissaValue = absVal;

        if (absVal >= 1) {
            while (mantissaValue >= 2) {
                mantissaValue /= 2;
                exponentActual++;
            }
        } else if (absVal > 0 && absVal < 1) {
            while (mantissaValue < 1) {
                mantissaValue *= 2;
                exponentActual--;
            }
        }

        // 3-bit exponent + bias=3
        int exponent = exponentActual + 3;
        if (exponent < 0) exponent = 0;
        if (exponent > 7) exponent = 7;
        String exponentBinary = String.format("%3s", Integer.toBinaryString(exponent)).replace(' ', '0');

        // Mantissa 4-bit (fractional)
        double fraction = mantissaValue - 1; // remove leading 1
        StringBuilder mantissa = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            fraction *= 2;
            if (fraction >= 1) {
                mantissa.append("1");
                fraction -= 1;
            } else {
                mantissa.append("0");
            }
        }

        binary8 = "" + sign + exponentBinary + mantissa.toString();
    }

    // Binary → Decimal
    private void convertBinaryToDecimal(String binary) {
        int sign = binary.charAt(0) == '1' ? -1 : 1;
        int exponent = Integer.parseInt(binary.substring(1, 4), 2);
        exponent -= 3; // remove bias

        String mantissaStr = binary.substring(4);
        double mantissa = 1; // normalized, leading 1
        for (int i = 0; i < mantissaStr.length(); i++) {
            if (mantissaStr.charAt(i) == '1') {
                mantissa += Math.pow(2, -(i + 1));
            }
        }

        result = sign * mantissa * Math.pow(2, exponent);
        binary8 = binary;
    }

    public double getResult() {
        return result;
    }

    public String getBinary8() {
        return binary8;
    }

    // Test
    public static void main(String[] args) {
        BinaryConverter converter = new BinaryConverter("01011101");
        converter.splitParts();
        System.out.println("Decimal: " + converter.getResult()); // 7.25

        BinaryConverter converter2 = new BinaryConverter("7.25");
        converter2.splitParts();
        System.out.println("Binary: " + converter2.getBinary8()); // 01011101
    }
}
