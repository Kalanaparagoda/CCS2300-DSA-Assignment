package module2;

import java.util.*;

public class SortComparison {

    public void run(Scanner sc) {
        while (true) {
            System.out.println("\n======================================");
            System.out.println(" Module 2 - Sorting Algorithm Comparison");
            System.out.println("======================================");
            System.out.println("1. Enter numbers manually");
            System.out.println("2. Generate random dataset");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose: ");

            int choice = readInt(sc);

            switch (choice) {
                case 1 -> compareWithManualInput(sc);
                case 2 -> compareWithRandom(sc);
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void compareWithManualInput(Scanner sc) {
        System.out.print("How many numbers? ");
        int n = readPositiveInt(sc);

        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter number " + (i + 1) + ": ");
            data[i] = readInt(sc);
        }

        runComparison(data);
    }

    private void compareWithRandom(Scanner sc) {
        System.out.print("Dataset size: ");
        int n = readPositiveInt(sc);

        System.out.print("Min value: ");
        int min = readInt(sc);

        System.out.print("Max value: ");
        int max = readInt(sc);

        if (min > max) {
            int t = min; min = max; max = t;
        }

        Random rnd = new Random();
        int[] data = new int[n];
        for (int i = 0; i < n; i++) {
            data[i] = rnd.nextInt(max - min + 1) + min;
        }

        System.out.println("Random dataset created.");
        runComparison(data);
    }

    private void runComparison(int[] data) {
        long t1, t2;

        t1 = System.nanoTime();
        int[] bubble = SortingAlgorithms.bubbleSort(data);
        t2 = System.nanoTime();
        long bubbleTime = (t2 - t1);

        t1 = System.nanoTime();
        int[] merge = SortingAlgorithms.mergeSort(data);
        t2 = System.nanoTime();
        long mergeTime = (t2 - t1);

        t1 = System.nanoTime();
        int[] quick = SortingAlgorithms.quickSort(data);
        t2 = System.nanoTime();
        long quickTime = (t2 - t1);

        System.out.println("\nSorted Output (Quick Sort):");
        printArray(quick);

        System.out.println("\n--- Performance Comparison (nanoseconds) ---");
        System.out.printf("%-12s | %-15s%n", "Algorithm", "Time (ns)");
        System.out.println("-------------------------------");
        System.out.printf("%-12s | %-15d%n", "Bubble", bubbleTime);
        System.out.printf("%-12s | %-15d%n", "Merge", mergeTime);
        System.out.printf("%-12s | %-15d%n", "Quick", quickTime);
    }

    private void printArray(int[] a) {
        if (a.length == 0) {
            System.out.println("[]");
            return;
        }
        System.out.print("[");
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
            if (i < a.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }

    private int readInt(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    private int readPositiveInt(Scanner sc) {
        while (true) {
            int x = readInt(sc);
            if (x > 0) return x;
            System.out.print("Enter a positive number (>0): ");
        }
    }
}
