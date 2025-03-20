package br.pucrs;

import java.util.Arrays;
import java.util.Random;

public class App {
    public static long mergeSortIterations = 0;
    public static long maxVal1Iterations = 0;
    public static long maxVal2Iterations = 0;
    public static long multiplyIterations = 0;

    public static int[] mergeSort(int[] arr) {
        mergeSortIterations++;
        if (arr.length <= 1)
            return arr;

        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        left = mergeSort(left);
        right = mergeSort(right);

        return merge(left, right);
    }

    private static int[] merge(int[] left, int[] right) {
        int[] merged = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            mergeSortIterations++;
            if (left[i] <= right[j])
                merged[k++] = left[i++];
            else
                merged[k++] = right[j++];
        }
        while (i < left.length) {
            mergeSortIterations++;
            merged[k++] = left[i++];
        }
        while (j < right.length) {
            mergeSortIterations++;
            merged[k++] = right[j++];
        }
        return merged;
    }

    public static long maxVal1(long[] A) {
        maxVal1Iterations = 0;
        long max = A[0];
        for (int i = 1; i < A.length; i++) {
            maxVal1Iterations++;
            if (A[i] > max)
                max = A[i];
        }
        return max;
    }

    public static long maxVal2(long[] A, int init, int end) {
        maxVal2Iterations++;
        if (end - init <= 1) {
            return Math.max(A[init], A[end]);
        } else {
            int m = (init + end) / 2;
            long v1 = maxVal2(A, init, m);
            long v2 = maxVal2(A, m + 1, end);
            return Math.max(v1, v2);
        }
    }

    public static long multiply(long x, long y, int n) {
        multiplyIterations++;
        if (n == 1)
            return x * y;
        else {
            int m = (n + 1) / 2;
            long pow = 1L << m;
            long a = x / pow;
            long b = x % pow;
            long c = y / pow;
            long d = y % pow;

            long e = multiply(a, c, m);
            long f = multiply(b, d, m);
            long g = multiply(b, c, m);
            long h = multiply(a, d, m);

            return ((1L << (2 * m)) * e) + ((1L << m) * (g + h)) + f;
        }
    }

    public static int[] randomIntArray(int size) {
        Random rand = new Random();
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(10000);
        }
        return arr;
    }

    public static long[] randomLongArray(int size) {
        Random rand = new Random();
        long[] arr = new long[size];
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextLong();
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] sizes = { 32, 2048, 1048576 };

        System.out.println("Merge Sort Tests:");
        for (int size : sizes) {
            int[] arr = randomIntArray(size);
            mergeSortIterations = 0;
            long start = System.nanoTime();
            int[] sorted = mergeSort(arr);
            long duration = System.nanoTime() - start;
            System.out.println("Size: " + size + " | Iterations: " + mergeSortIterations + " | Time (ns): " + duration);
        }

        System.out.println("\nMax Value (Iterative) Tests:");
        for (int size : sizes) {
            long[] arr = randomLongArray(size);
            maxVal1Iterations = 0;
            long start = System.nanoTime();
            long max1 = maxVal1(arr);
            long duration = System.nanoTime() - start;
            System.out.println("Size: " + size + " | Max: " + max1 + " | Iterations: " + maxVal1Iterations
                    + " | Time (ns): " + duration);
        }

        System.out.println("\nMax Value (Divide and Conquer) Tests:");
        for (int size : sizes) {
            long[] arr = randomLongArray(size);
            maxVal2Iterations = 0;
            long start = System.nanoTime();
            long max2 = maxVal2(arr, 0, arr.length - 1);
            long duration = System.nanoTime() - start;
            System.out.println("Size: " + size + " | Max: " + max2 + " | Iterations: " + maxVal2Iterations
                    + " | Time (ns): " + duration);
        }

        System.out.println("\nMultiplication Tests:");
        int[] bitSizes = { 4, 16, 64 };
        Random rand = new Random();
        for (int nBits : bitSizes) {
            long maxValue;
            if (nBits == 64)
                maxValue = Long.MAX_VALUE;
            else
                maxValue = (1L << nBits) - 1;

            long x = Math.abs(rand.nextLong()) % maxValue;
            long y = Math.abs(rand.nextLong()) % maxValue;
            multiplyIterations = 0;
            long start = System.nanoTime();
            long product = multiply(x, y, nBits);
            long duration = System.nanoTime() - start;
            System.out.println("Bits: " + nBits + " | x: " + x + " y: " + y + " | Product: " + product
                    + " | Iterations: " + multiplyIterations + " | Time (ns): " + duration);
        }
    }
}