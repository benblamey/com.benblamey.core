package com.benblamey.core;

import java.util.List;

/**
 * Utility mathematics.
 *
 * @author Ben Blamey ben@benblamey.com
 *
 */
public class Math2 {

    /**
     * "distance around the circle" under modulo arithmetic.
     *
     * @param a
     * @param b
     * @param mod
     * @return
     * @throws NotImplemented
     */
    public static double distUndermod(double a, double b, double mod) {

        if (mod < 0) {
            throw new IllegalArgumentException("mod must be positive");
        }

        // compute difference in each direction.
        double diff_x = a - b;
        double diff_y = b - a;

        // take mode.
        diff_x = diff_x % mod;
        diff_y = diff_y % mod;

        // ensure +ve.
        if (diff_x < 0) {
            diff_x += mod;
        }
        if (diff_y < 0) {
            diff_y += mod;
        }

        return Math.min(diff_x, diff_y);
    }

    /**
     * Compute the standard deviation under modulo arithmetic.
     *
     * @param values
     * @param mean
     * @param mod
     * @return
     * @throws NotImplemented
     */
    public static double standardDeviationUnderModulo(List<Double> values, double mean, double mod) {

        double total = 0;
        int n = 0;

        for (Double x : values) {
            double distSeconds = Math2.distUndermod(x, mean, mod);
            total += distSeconds * distSeconds;
            n++;
        }

        return Math.sqrt(total / n);

    }
}
