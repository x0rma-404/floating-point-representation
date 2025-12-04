package com.example.myapplication;

public class BinaryReverse {

    private double decimal;       // daxil edilən ədəd (7.25)
    private String binary8 = "";  // çıxış: 8-bit 01011101

    public BinaryReverse(double decimal) {
        this.decimal = decimal;
    }

    // MainActivity convert(10) çağırır — fractionalBits-i istifadə etmirik
    public void convert(int fractionalBits) {
        binary8 = convertDecimalTo8Bit(decimal);
    }

    public String getBinaryString() {
        return binary8;
    }

    // ------------------------
    // Decimal → 8-bit floating-point
    // ------------------------
    private String convertDecimalTo8Bit(double num) {

        int sign = num < 0 ? 1 : 0;
        double abs = Math.abs(num);

        // --- Normalize ---
        int exponentActual = 0;
        double mantissaValue = abs;

        if (abs >= 1) {
            while (mantissaValue >= 2) {
                mantissaValue /= 2;
                exponentActual++;
            }
        } else if (abs > 0 && abs < 1) {
            while (mantissaValue < 1) {
                mantissaValue *= 2;
                exponentActual--;
            }
        }

        // Exponent (3 bit + bias=3)
        int exponent = exponentActual + 3;
        if (exponent < 0) exponent = 0;
        if (exponent > 7) exponent = 7;

        String exponentBin = String.format("%3s",
                Integer.toBinaryString(exponent)).replace(' ', '0');

        // Mantissa (4 bit)
        double fraction = mantissaValue - 1;
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

        return "" + sign + exponentBin + mantissa.toString();
    }
}
