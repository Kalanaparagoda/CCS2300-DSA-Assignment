package module3;

import java.util.Random;
import java.util.Scanner;
import module2.SortingAlgorithms;

public class PerformanceAnalyzer {

    public void run(Scanner sc) {
        while (true) {
            System.out.println("\n====================================");
            System.out.println(" Module 3 - Performance Analyzer");
            System.out.println("====================================");
            System.out.println("1. Run analysis (sizes: 100, 500, 1000)");
            System.out.println("2. Run analysis (custom sizes)");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose: ");

            int choice = readInt(sc);

            switch (choice) {
                case 1 -> analyze(new int[]{100, 500, 1000});
                case 2 -> analyze(customSizes(sc));
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private int[] customSizes(Scanner sc) {
        System.out.print("How many test sizes? ");
        int k = readPositiveInt(sc);

        int[] sizes = new int[k];
        for (int i = 0; i < k; i++) {
            System.out.print("Enter size " + (i + 1) + ": ");
            sizes[i] = readPositiveInt(sc);
        }
        return sizes;
    }

    private void analyze(int[] sizes) {

        Random rnd = new Random();
        int runs = 5;  

        System.out.println("\n--- Running Performance Tests ---");
        System.out.printf("%-10s | %-18s | %-18s%n",
                "N", "QuickSort (ns)", "BinarySearch (ns)");
        System.out.println("----------------------------------------------------------");

        for (int n : sizes) {

            long totalSortTime = 0;
            long totalSearchTime = 0;

            for (int r = 0; r < runs; r++) {

                int[] data = new int[n];
                for (int i = 0; i < n; i++)
                    data[i] = rnd.nextInt(1_000_000);

                long t1 = System.nanoTime();
                int[] sorted = SortingAlgorithms.quickSort(data);
                long t2 = System.nanoTime();
                totalSortTime += (t2 - t1);

                int target = sorted[rnd.nextInt(sorted.length)];

                long s1 = System.nanoTime();
                int idx = SearchAlgorithms.binarySearch(sorted, target);
                long s2 = System.nanoTime();
                totalSearchTime += (s2 - s1);

                if (idx < 0) System.out.print("");
            }

            long avgSort = totalSortTime / runs;
            long avgSearch = totalSearchTime / runs;

            System.out.printf("%-10d | %-18d | %-18d%n",
                    n, avgSort, avgSearch);
        }

        System.out.println("\nObservation:");
        System.out.println("- Sorting time increases with input size (O(n log n)).");
        System.out.println("- Binary Search remains very fast (O(log n)).");

        demonstrateBST();
    }
    
    private void demonstrateBST() {
        System.out.println("\n--- Demonstrating BST (Tree-Based Data Organization) ---");

        BST tree = new BST();
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);

        System.out.println("Searching 40 in BST: " + tree.search(40));
        System.out.println("Searching 90 in BST: " + tree.search(90));
    }

    private int readInt(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
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
