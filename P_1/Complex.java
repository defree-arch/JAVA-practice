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
        return new Complex((real * B.real + imag * B.imag)/del, (real * B.imag - B.real * imag)/del);
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
}