import java.util.Scanner;

import module1.RoutePlanner;
import module2.SortComparison;
import module3.PerformanceAnalyzer;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        RoutePlanner routePlanner = new RoutePlanner();
        SortComparison sortComparison = new SortComparison();
        PerformanceAnalyzer performanceAnalyzer = new PerformanceAnalyzer();

        while (true) {
            System.out.println("\n====================================");
            System.out.println(" CCS2300 - DSA Assignment (Main Menu)");
            System.out.println("====================================");
            System.out.println("1. Module 1 - Smart City Route Planner (Graphs + Trees)");
            System.out.println("2. Module 2 - Data Sorter (Sort Comparison)");
            System.out.println("3. Module 3 - Algorithm Performance Analyzer");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            int choice = readInt(sc);

            switch (choice) {
                case 1 -> routePlanner.run(sc);
                case 2 -> sortComparison.run(sc);
                case 3 -> performanceAnalyzer.run(sc);
                case 0 -> {
                    System.out.println("Bye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static int readInt(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
