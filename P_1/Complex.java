public class Complex {
    //fields
    private final double imag;
    private final double real;

    //constructor
    public Complex(double real, double imag) {
        this.real = real;
        this.imag = imag;
    }

    //add
    public Complex add (Complex B) {
        return new Complex(real + B.real, imag + B.imag);
    }

    //sub
    public Complex sub (Complex B) {
        return new Complex(real - B.real, imag - B.imag);
    }

    //multiply
    public Complex multiply (Complex B) {
        return new Complex(real * B.real - imag * B.imag, real * B.imag + B.real * imag);
    }

    //division
    public Complex div (Complex B) {
        double del = B.real * B.real + B.imag * B.imag;
        if (del == 0) throw new ArithmeticException("Division by zero");
        return new Complex((real * B.real + imag * B.imag)/del, (imag * B.real - real * B.imag)/del);
    }

    //test for zero
    public boolean is_zero() {
        return real == 0 && imag == 0;
    }

    //absolute complex
    public double abs() {
        return Math.sqrt(real * real + imag * imag);
    }

    //conjugate
    public Complex conjugate() {
        return new Complex(real, -imag);
    }

    public String to_string() {
        if (imag == 0) return String.valueOf(real);
        if (real == 0) return imag + "i";
        return real + (imag > 0 ? "+" : "") + imag + "i";
    }

    //parsing input
    public static Complex parse(String s) {
        s = s.replace(" ", "");

        if (!s.contains("i")) return new Complex(Double.parseDouble(s), 0.0);

        s = s.substring(0, s.length() - 1);

        int symbol_index = -1;
        for (int k = 1; k < s.length(); k++) {
            if (s.charAt(k) == '+' || s.charAt(k) == '-') {
                symbol_index = k;
                break;
            }
        }

        if (symbol_index == -1) {
            if (s.isEmpty() || s.equals("+")) return new Complex(0.0, 1.0);
            if (s.isEmpty() || s.equals("-")) return new Complex(0.0, -1.0);
            return new Complex(0.0, Double.parseDouble(s));
        }

        double real = Double.parseDouble(s.substring(0, symbol_index));
        String imag = s.substring(symbol_index);
        if (imag.equals("+")) imag = "+1";
        if (imag.equals("-")) imag = "-1";
        return new Complex(real, Double.parseDouble(imag));   
    }
}