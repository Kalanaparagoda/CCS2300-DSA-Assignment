package module1;

import java.util.*;

public class Graph {

    private final Map<Integer, String> locations = new HashMap<>();
    private final Map<Integer, List<Integer>> adj = new HashMap<>();

    public boolean addLocation(int id, String name) {
        if (locations.containsKey(id)) return false;
        locations.put(id, name);
        adj.put(id, new ArrayList<>());
        return true;
    }

    public boolean removeLocation(int id) {
        if (!locations.containsKey(id)) return false;

        // remove edges pointing to this
        for (List<Integer> nbrs : adj.values()) {
            nbrs.removeIf(v -> v == id);
        }

        adj.remove(id);
        locations.remove(id);
        return true;
    }

    public boolean addRoad(int from, int to) {
        if (!locations.containsKey(from) || !locations.containsKey(to)) return false;
        if (from == to) return false;

        if (!adj.get(from).contains(to)) adj.get(from).add(to);
        if (!adj.get(to).contains(from)) adj.get(to).add(from); // undirected
        return true;
    }

    public boolean removeRoad(int from, int to) {
        if (!adj.containsKey(from) || !adj.containsKey(to)) return false;
        boolean removed = adj.get(from).remove((Integer) to);
        removed = adj.get(to).remove((Integer) from) || removed;
        return removed;
    }

    public void displayConnections() {
        if (locations.isEmpty()) {
            System.out.println("No locations in graph.");
            return;
        }

        System.out.println("\n--- Connections (Adjacency List) ---");
        List<Integer> ids = new ArrayList<>(locations.keySet());
        Collections.sort(ids);

        for (int id : ids) {
            System.out.print(id + " (" + locations.get(id) + ") -> ");
            List<Integer> nbrs = new ArrayList<>(adj.getOrDefault(id, List.of()));
            Collections.sort(nbrs);
            if (nbrs.isEmpty()) {
                System.out.println("No roads");
            } else {
                for (int i = 0; i < nbrs.size(); i++) {
                    int v = nbrs.get(i);
                    System.out.print(v + "(" + locations.get(v) + ")");
                    if (i < nbrs.size() - 1) System.out.print(", ");
                }
                System.out.println();
            }
        }
    }

    // Queue traversal (BFS)
    public void bfs(int startId) {
        if (!locations.containsKey(startId)) {
            System.out.println("Start location not found.");
            return;
        }

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> q = new ArrayDeque<>();
        q.add(startId);
        visited.add(startId);

        System.out.println("\nBFS Traversal:");
        while (!q.isEmpty()) {
            int u = q.poll();
            System.out.print(u + "(" + locations.get(u) + ")  ");

            for (int v : adj.getOrDefault(u, List.of())) {
                if (!visited.contains(v)) {
                    visited.add(v);
                    q.add(v);
                }
            }
        }
        System.out.println();
    }

    // Stack traversal (DFS)
    public void dfs(int startId) {
        if (!locations.containsKey(startId)) {
            System.out.println("Start location not found.");
            return;
        }

        Set<Integer> visited = new HashSet<>();
        Deque<Integer> st = new ArrayDeque<>();
        st.push(startId);

        System.out.println("\nDFS Traversal:");
        while (!st.isEmpty()) {
            int u = st.pop();
            if (visited.contains(u)) continue;
            visited.add(u);

            System.out.print(u + "(" + locations.get(u) + ")  ");

            // push neighbors (reverse sort to look nicer)
            List<Integer> nbrs = new ArrayList<>(adj.getOrDefault(u, List.of()));
            nbrs.sort(Collections.reverseOrder());
            for (int v : nbrs) {
                if (!visited.contains(v)) st.push(v);
            }
        }
        System.out.println();
    }

    public boolean hasLocation(int id) {
        return locations.containsKey(id);
    }

    public String getName(int id) {
        return locations.get(id);
    }

    public int size() {
        return locations.size();
    }
}
