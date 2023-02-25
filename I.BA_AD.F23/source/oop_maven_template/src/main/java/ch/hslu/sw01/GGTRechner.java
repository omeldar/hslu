package ch.hslu.sw01;

public class GGTRechner {
    public static int ggtIterativ1(int a, int b) {
        while (a != b) {
            if (a > b) {
                a = a - b;
            } else {
                b = b - a;
            }
        }
        return a;
    }
    public static int ggtIterativ2(int a, int b) {
        while ((a != 0) && (b != 0)) {
            if (a > b) {
                a = a % b;
            } else {
                b = b % a;
            }
        }
        return (a + b); // Entweder a oder b ist 0!
    }
    public static int ggtRekursiv(final int a, final int b) {
        if (a > b) {
            return ggtRekursiv(a - b, b);
        } else {
            if (a < b) {
                return ggtRekursiv(a, b - a);
            } else {
                return a;
            }
        }
    }
}
