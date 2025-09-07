package com.example.actividad_3.process;

public class FibonacciMax {

    // --- Recursividad directa (inocente, O(φ^n); NO usar para n grande) ---
    public long fibRecursive(int n) {
        if (n < 0) throw new IllegalArgumentException("n debe ser >= 0");
        if (n <= 1) return n;                    // casos base
        return fibRecursive(n - 1) + fibRecursive(n - 2); // caso recursivo directo
    }

    // --- Divide y vencerás (fast doubling), O(log n) ---
    public long fibFastDoubling(int n) {
        if (n < 0) throw new IllegalArgumentException("n debe ser >= 0");
        return pairFib(n).f;                     // devolvemos F(n)
    }

    // Calcula (F(n), F(n+1)) con fast doubling
    private FibPair pairFib(int n) {
        if (n == 0) return new FibPair(0L, 1L);
        FibPair half = pairFib(n / 2);
        long a = half.f;     // F(k)
        long b = half.next;  // F(k+1)

        // Fórmulas de duplicación:
        // F(2k)   = F(k) * (2*F(k+1) - F(k))
        // F(2k+1) = F(k)^2 + F(k+1)^2
        long c = a * (2L * b - a);       // F(2k)
        long d = a * a + b * b;          // F(2k+1)

        if ((n & 1) == 0) {              // n es par
            return new FibPair(c, d);
        } else {                         // n es impar
            return new FibPair(d, c + d);
        }
    }

    // Par interno para transportar (F(n), F(n+1))
    private static class FibPair {
        final long f;
        final long next;
        FibPair(long f, long next) { this.f = f; this.next = next; }
    }
}

    
