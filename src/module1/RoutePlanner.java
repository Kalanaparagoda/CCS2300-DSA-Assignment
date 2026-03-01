package module1;

import java.util.List;
import java.util.Scanner;

public class RoutePlanner {

    private final LocationTree tree = new LocationTree();
    private final Graph graph = new Graph();

    public void run(Scanner sc) {
        while (true) {
            System.out.println("\n===============================");
            System.out.println(" Module 1 - Smart City Planner");
            System.out.println("===============================");
            System.out.println("1. Add location (stored in Tree, then mapped to Graph)");
            System.out.println("2. Remove location");
            System.out.println("3. Add road (edge)");
            System.out.println("4. Remove road (edge)");
            System.out.println("5. Display all connections");
            System.out.println("6. BFS Traversal (Queue)");
            System.out.println("7. DFS Traversal (Stack)");
            System.out.println("8. Display Tree (in-order)");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose: ");

            int choice = readInt(sc);

            switch (choice) {
                case 1 -> addLocation(sc);
                case 2 -> removeLocation(sc);
                case 3 -> addRoad(sc);
                case 4 -> removeRoad(sc);
                case 5 -> graph.displayConnections();
                case 6 -> {
                    System.out.print("Start location ID: ");
                    graph.bfs(readInt(sc));
                }
                case 7 -> {
                    System.out.print("Start location ID: ");
                    graph.dfs(readInt(sc));
                }
                case 8 -> displayTree();
                case 0 -> { return; }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void addLocation(Scanner sc) {
        System.out.print("Enter location ID (number): ");
        int id = readInt(sc);

        System.out.print("Enter location name: ");
        String name = sc.nextLine().trim();
        while (name.isEmpty()) {
            System.out.print("Name cannot be empty. Enter location name: ");
            name = sc.nextLine().trim();
        }

        boolean okTree = tree.insert(id, name);
        if (!okTree) {
            System.out.println("Location ID already exists in Tree.");
            return;
        }

        // map into graph
        boolean okGraph = graph.addLocation(id, name);
        if (okGraph) System.out.println("Location added successfully (Tree + Graph).");
        else System.out.println("Graph add failed (unexpected).");
    }

    private void removeLocation(Scanner sc) {
        System.out.print("Enter location ID to remove: ");
        int id = readInt(sc);

        boolean okTree = tree.delete(id);
        boolean okGraph = graph.removeLocation(id);

        if (okTree && okGraph) System.out.println("Location removed from Tree + Graph.");
        else System.out.println("Location not found.");
    }

    private void addRoad(Scanner sc) {
        if (graph.size() < 2) {
            System.out.println("Need at least 2 locations to add a road.");
            return;
        }

        System.out.print("From location ID: ");
        int from = readInt(sc);
        System.out.print("To location ID: ");
        int to = readInt(sc);

        boolean ok = graph.addRoad(from, to);
        if (ok) System.out.println("Road added successfully.");
        else System.out.println("Failed: Check IDs (must exist) and cannot connect same node.");
    }

    private void removeRoad(Scanner sc) {
        System.out.print("From location ID: ");
        int from = readInt(sc);
        System.out.print("To location ID: ");
        int to = readInt(sc);

        boolean ok = graph.removeRoad(from, to);
        if (ok) System.out.println("Road removed successfully.");
        else System.out.println("Road not found / invalid IDs.");
    }

    private void displayTree() {
        if (tree.isEmpty()) {
            System.out.println("Tree is empty.");
            return;
        }
        System.out.println("\n--- Tree In-Order (sorted by ID) ---");
        List<LocationTree.Node> nodes = tree.inOrder();
        for (LocationTree.Node n : nodes) {
            System.out.println(n.id + " -> " + n.name);
        }
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
}
